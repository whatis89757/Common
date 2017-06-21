package com.cttdy.common.util;

import android.net.Uri;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yejingxian on 2017/6/20.
 */

public class FileDownloadUtils {
    /**
     * Regex used to parse content-disposition headers
     */
    private static final Pattern CONTENT_DISPOSITION_PATTERN =
            Pattern.compile("attachment;\\s*filename\\s*=\\s*\"([^\"]*)\"");
    private static final Object sUniqueLock = new Object();
    private static Random sRandom = new Random(SystemClock.uptimeMillis());

    private static final String DEFAULT_DL_FILENAME = "downloadfile";
    /**
     * The default extension for html files if we can't get one at the HTTP level
     */
    private static final String DEFAULT_DL_HTML_EXTENSION = ".html";

    /**
     * The default extension for text files if we can't get one at the HTTP level
     */
    private static final String DEFAULT_DL_TEXT_EXTENSION = ".txt";

    /**
     * The default extension for binary files if we can't get one at the HTTP level
     */
    private static final String DEFAULT_DL_BINARY_EXTENSION = ".bin";
    /**
     * When a number has to be appended to the filename, this string is used to separate the
     * base filename from the sequence number
     */
    private static final String FILENAME_SEQUENCE_SEPARATOR = "-";


    /**
     * 生成文件保存路径
     *
     * @param url
     * @param parent
     * @param hint
     * @param contentDisposition
     * @param contentLocation
     * @param mimeType
     * @return
     * @throws IOException
     */
    public static String generateSaveFile(String url, @NonNull File parent, @Nullable String hint,
                                          @Nullable String contentDisposition, @Nullable String contentLocation,
                                          @Nullable String mimeType, boolean replaceOld) throws IOException {
        String name = chooseFilename(url, hint, contentDisposition, contentLocation, DEFAULT_DL_FILENAME);
        String prefix = null;
        String suffix = null;
        final int dotIndex = name.lastIndexOf('.');
        final boolean missingExtension = dotIndex < 0;
        if (missingExtension) {
            prefix = name;
            suffix = chooseExtensionFromMimeType(mimeType, true);
        } else {
            prefix = name.substring(0, dotIndex);
            suffix = chooseExtensionFromFilename(mimeType, name, dotIndex);
        }
        synchronized (sUniqueLock) {
            if (!replaceOld) {
                name = generateAvailableFilenameLocked(parent, prefix, suffix);
            }
            // Claim this filename inside lock to prevent other threads from
            // clobbering us. We're not paranoid enough to use O_EXCL.
            final File file = new File(parent, name);
            file.deleteOnExit();
            file.createNewFile();
            return file.getAbsolutePath();
        }
    }

    private static String generateAvailableFilenameLocked(
            @NonNull File parent, @NonNull String prefix, @NonNull String suffix) throws IOException {
        String name = prefix + suffix;
        if (isFilenameAvailableLocked(parent, name)) {
            return name;
        }
        int sequence = 1;
        for (int magnitude = 1; magnitude < 1000000000; magnitude *= 10) {
            for (int iteration = 0; iteration < 9; ++iteration) {
                name = prefix + FILENAME_SEQUENCE_SEPARATOR + sequence + suffix;
                if (isFilenameAvailableLocked(parent, name)) {
                    return name;
                }
                sequence += sRandom.nextInt(magnitude) + 1;
            }
        }
        throw new IOException("Failed to generate an available filename");
    }

    private static boolean isFilenameAvailableLocked(File parent, String name) {
        if (new File(parent, name).exists()) {
            return false;
        }
        return true;
    }

    /**
     * 生成后缀
     *
     * @param name
     * @param mimeType
     * @return
     */
    public static String chosseExtension(@NonNull String name, @Nullable String mimeType) {
        String suffix = null;
        final int dotIndex = name.lastIndexOf('.');
        final boolean missingExtension = dotIndex < 0;
        if (missingExtension) {
            suffix = chooseExtensionFromMimeType(mimeType, false);
        } else {
            suffix = chooseExtensionFromFilename(mimeType, name, dotIndex);
        }
        return suffix;
    }

    /**
     * 根据文件名生成后缀
     *
     * @param mimeType
     * @param filename
     * @param lastDotIndex
     * @return
     */
    private static String chooseExtensionFromFilename(@Nullable String mimeType, @NonNull String filename, int lastDotIndex) {
        String extension = null;
        if (mimeType != null) {
            // Compare the last segment of the extension against the mime type.
            // If there's a mismatch, discard the entire extension.
            String typeFromExt = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                    filename.substring(lastDotIndex + 1));
            if (typeFromExt == null || !typeFromExt.equalsIgnoreCase(mimeType)) {
                extension = chooseExtensionFromMimeType(mimeType, false);
                if (extension != null) {
                } else {
                }
            }
        }
        if (extension == null) {
            extension = filename.substring(lastDotIndex);
        }
        return extension;
    }

    /**
     * 根据mimeType生成后缀
     *
     * @param mimeType
     * @param useDefaults
     * @return
     */
    private static String chooseExtensionFromMimeType(@Nullable String mimeType, boolean useDefaults) {
        String extension = null;
        if (mimeType != null) {
            extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType);
            if (extension != null) {
                extension = "." + extension;
            } else {
            }
        }
        if (extension == null) {
            if (mimeType != null && mimeType.toLowerCase().startsWith("text/")) {
                if (mimeType.equalsIgnoreCase("text/html")) {
                    extension = DEFAULT_DL_HTML_EXTENSION;
                } else if (useDefaults) {
                    extension = DEFAULT_DL_TEXT_EXTENSION;
                }
            } else if (useDefaults) {
                extension = DEFAULT_DL_BINARY_EXTENSION;
            }
        }
        return extension;
    }

    /**
     * 生成文件名
     *
     * @param url
     * @param hint
     * @param contentDisposition
     * @param contentLocation
     * @return
     */
    public static String chooseFilename(String url, String hint, String contentDisposition,
                                        String contentLocation, String def) {
        String filename = null;

        // First, try to use the hint from the application, if there's one
        if (filename == null && hint != null && !hint.endsWith("/")) {
            int index = hint.lastIndexOf('/') + 1;
            if (index > 0) {
                filename = hint.substring(index);
            } else {
                filename = hint;
            }
        }

        // If we couldn't do anything with the hint, move toward the content disposition
        if (filename == null && contentDisposition != null) {
            filename = parseContentDisposition(contentDisposition);
            if (filename != null) {
                int index = filename.lastIndexOf('/') + 1;
                if (index > 0) {
                    filename = filename.substring(index);
                }
            }
        }

        // If we still have nothing at this point, try the content location
        if (filename == null && contentLocation != null) {
            String decodedContentLocation = Uri.decode(contentLocation);
            if (decodedContentLocation != null
                    && !decodedContentLocation.endsWith("/")
                    && decodedContentLocation.indexOf('?') < 0) {
                int index = decodedContentLocation.lastIndexOf('/') + 1;
                if (index > 0) {
                    filename = decodedContentLocation.substring(index);
                } else {
                    filename = decodedContentLocation;
                }
            }
        }

        // If all the other http-related approaches failed, use the plain uri
        if (filename == null) {
            String decodedUrl = Uri.decode(url);
            if (decodedUrl != null
                    && !decodedUrl.endsWith("/") && decodedUrl.indexOf('?') < 0) {
                int index = decodedUrl.lastIndexOf('/') + 1;
                if (index > 0) {
                    filename = decodedUrl.substring(index);
                }
            }
        }

        // Finally, if couldn't get filename from URI, get a generic filename
        if (filename == null) {
            filename = def;
        }

        // The VFAT file system is assumed as target for downloads.
        // Replace invalid characters according to the specifications of VFAT.
        filename = buildValidFatFilename(filename);

        return filename;
    }


    /**
     * 转化文件名使合法
     * Mutate the given filename to make it valid for a FAT filesystem,
     * replacing any invalid characters with "_".
     */
    public static String buildValidFatFilename(String name) {
        if (TextUtils.isEmpty(name) || ".".equals(name) || "..".equals(name)) {
            return name;
        }
        final StringBuilder res = new StringBuilder(name.length());
        for (int i = 0; i < name.length(); i++) {
            final char c = name.charAt(i);
            if (isValidFatFilenameChar(c)) {
                res.append(c);
            } else {
                res.append('_');
            }
        }
        return res.toString();
    }

    /**
     * 文件名是否合法
     *
     * @param name
     * @return
     */
    public static boolean isValidFatFilename(String name) {
        return (name != null) && name.equals(buildValidFatFilename(name));
    }

    /**
     * 是否文件名允许的字符
     *
     * @param c
     * @return
     */
    private static boolean isValidFatFilenameChar(char c) {
        if ((0x00 <= c && c <= 0x1f)) {
            return false;
        }
        switch (c) {
            case '"':
            case '*':
            case '/':
            case ':':
            case '<':
            case '>':
            case '?':
            case '\\':
            case '|':
            case 0x7F:
                return false;
            default:
                return true;
        }
    }

    /**
     * 提取Content-disposition的文件名
     *
     * @param contentDisposition
     * @return
     */
    public static String parseContentDisposition(String contentDisposition) {
        try {
            Matcher m = CONTENT_DISPOSITION_PATTERN.matcher(contentDisposition);
            if (m.find()) {
                return m.group(1);
            }
        } catch (IllegalStateException ex) {
            // This function is defined as returning null when it can't parse the header
        }
        return null;
    }
}
