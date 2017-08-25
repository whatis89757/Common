package com.cttdy.common.constant;

/**
 * Created by yejingxian on 2017/6/22.
 */

public class HttpCode {
	//正常
    public static final int HTTP_OK = 200;
	//已创建,表示服务器在请求的响应中建立了新文档；应在定位头信息中给出它的URL。
    public static final int HTTP_CREATED = 201;
	//接受,告诉客户端请求正在被执行，但还没有处理完
    public static final int HTTP_ACCEPTED = 202;
	//非官方信息,文档被正常的返回，但是由于正在使用的是文档副本所以某些响应头信息可能不正确。
    public static final int HTTP_NOT_AUTHORITATIVE = 203;
	//无内容,在并没有新文档的情况下，确保浏览器继续显示先前的文档。这各状态码对于用户周期性的重载某一页非常有用，并且你可以确定先前的页面是否已经更新。
    public static final int HTTP_NO_CONTENT = 204;
	//重置内容,虽然没有新文档但浏览器要重置文档显示。这个状态码用于强迫浏览器清除表单域。
    public static final int HTTP_RESET = 205;
    //局部内容,断点下载,服务器完成了一个包含Range头信息的局部请求时被发送的。
    public static final int HTTP_PARTIAL = 206;
	
	
	//多重选择,被请求的文档可以在多个地方找到，并将在返回的文档中列出来。如果服务器有首选设置，首选项将会被列于定位响应头信息中。 
    public static final int HTTP_MULT_CHOICE = 300;
	//请求的文档在别的地方；文档新的URL会在定位响应头信息中给出。浏览器会自动连接到新的URL.会包含一个Location的响应头信息,如果请求method不是GET或者HEAD，那么浏览器是禁止自动重定向的，除非得到用户的确认
	//浏览器一般直接自动用GET请求location中的url
    public static final int HTTP_MOVED_PERM = 301;
	//请求的文档在别的地方,只是定位头信息中所给的URL应被理解为临时交换地址而不是永久的.会包含一个Location的响应头信息,如果请求method不是GET或者HEAD，那么浏览器是禁止自动重定向的，除非得到用户的确认
	//浏览器一般直接自动用GET请求location中的url
    public static final int HTTP_MOVED_TEMP = 302;
	//参见其他信息,这个状态码和 301、302 相似，只是如果最初的请求是 POST，那么新文档（在定位头信息中给出）要用 GET 找回。会包含一个Location的响应头信息
    public static final int HTTP_SEE_OTHER = 303;
    public static final int HTTP_NOT_MODIFIED = 304;
	//使用代理,表示所请求的文档要通过定位头信息中的代理服务器获得。
    public static final int HTTP_USE_PROXY = 305;
	//临时重定向,浏览器处理307状态的规则与301，302相同。除GET、HEAD方法外，其他的请求方法必须等客户确认才能跳转。 
	public static final int HTTP_TEMP_REDIRECT = 307;
	
	
	//错误请求,客户端请求中的语法错误
    public static final int HTTP_BAD_REQUEST = 400;
	//未授权,表示客户端在授权头信息中没有有效的身份信息时访问受到密码保护的页面。这个响应必须包含一个WWW-Authenticate的授权信息头
    public static final int HTTP_UNAUTHORIZED = 401;
	//需付费
    public static final int HTTP_PAYMENT_REQUIRED = 402;
	//禁止,除非拥有授权否则服务器拒绝提供所请求的资源。这个状态经常会由于服务器上的损坏文件或目录许可而引起。 
    public static final int HTTP_FORBIDDEN = 403;
	//未找到,所给的地址无法找到任何资源。它是表示“没有所访问页面”的标准方式
    public static final int HTTP_NOT_FOUND = 404;
	//方法未允许,请求方法(GET, POST, HEAD, PUT, DELETE, 等)对某些特定的资源不允许使用
    public static final int HTTP_BAD_METHOD = 405;
	//无法访问,表示请求资源的MIME类型与客户端中Accept头信息中指定的类型不一致。
    public static final int HTTP_NOT_ACCEPTABLE = 406;
	//代理服务器认证要求,与401状态有些相似，只是这个状态用于代理服务器。该状态指出客户端必须通过代理服务器的认证。代理服务器返回一个Proxy-Authenticate响应头信息给客户端，这会引起客户端使用带有Proxy-Authorization请求的头信息重新连接。
    public static final int HTTP_PROXY_AUTH = 407;
	//请求超时,服务端等待客户端发送请求的时间过长
    public static final int HTTP_CLIENT_TIMEOUT = 408;
	//冲突,该状态通常与PUT请求一同使用，常被用于试图上传版本不正确的文件时
    public static final int HTTP_CONFLICT = 409;
	//已经不存在,告诉客户端所请求的文档已经不存在并且没有更新的地址。410状态不同于404，410是在指导文档已被移走的情况下使用，而404则用于未知原因的无法访问
    public static final int HTTP_GONE = 410;
	//需要数据长度,表示服务器不能处理请求（假设为带有附件的POST请求），除非客户端发送Content-Length头信息指出发送给服务器的数据的大小
    public static final int HTTP_LENGTH_REQUIRED = 411;
	//先决条件错误,请求头信息中的某些先决条件是错误的
    public static final int HTTP_PRECON_FAILED = 412;
	//请求实体过大,告诉客户端现在所请求的文档比服务器现在想要处理的要大。如果服务器认为能够过一段时间处理，则会包含一个Retry-After的响应头信息
    public static final int HTTP_ENTITY_TOO_LARGE = 413;
	//请求URI过长,用于在URI过长的情况时。这里所指的“URI”是指URL中主机、域名及端口号之后的内容。例如：在URL--http://www.y2k-disaster.com:8080/we/look/silly/now/中URI是指/we/look/silly/now/
    public static final int HTTP_REQ_TOO_LONG = 414;
	//不支持的媒体格式
    public static final int HTTP_UNSUPPORTED_TYPE = 415;
	//请求范围无法满足,表示客户端包含了一个服务器无法满足的Range头信息的请求。
	public static final int HTTP_REQUESTED_RANGE_NOT_SATISFIABLE = 416;

	
	//内部服务器错误,常用的“服务器错误”状态。该状态经常由CGI程序引起也可能（但愿不会如此！）由无法正常运行的或返回头信息格式不正确的servlet引起
    public static final int HTTP_INTERNAL_ERROR = 500;
    @Deprecated
    public static final int HTTP_SERVER_ERROR = 500;
	//未实现.告诉客户端服务器不支持请求中要求的功能。例如，客户端执行了如PUT这样的服务器并不支持的命令。
    public static final int HTTP_NOT_IMPLEMENTED = 501;
	//错误的网关,被用于充当代理或网关的服务器；该状态指出接收服务器接收到远端服务器的错误响应。 
    public static final int HTTP_BAD_GATEWAY = 502;
    //由于临时的服务器维护或者过载，服务器当前无法处理请求。响应中可以包含一个 Retry-After 头用以标明这个延迟时间
    public static final int HTTP_UNAVAILABLE = 503;
	//网关超时,该状态也用于充当代理或网关的服务器；它指出接收服务器没有从远端服务器得到及时的响应。
    public static final int HTTP_GATEWAY_TIMEOUT = 504;
	//不支持的 HTTP 版本,服务器并不支持在请求中所标明 HTTP 版本。
    public static final int HTTP_VERSION = 505;

}
