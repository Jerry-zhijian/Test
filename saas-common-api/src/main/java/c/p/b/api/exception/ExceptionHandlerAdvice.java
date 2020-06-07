package c.p.b.api.exception;

import c.p.b.api.response.MessageResponse;
import c.p.b.api.response.Response;
import c.p.b.api.utils.JsonUtils;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.sql.SQLException;

/**
 * Global exception auto handler resolver.
 * <p>
 * Created: 2016-11-20 10:08:35
 *
 * @author Michael.Zhang
 */
@RestControllerAdvice
public class ExceptionHandlerAdvice {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlerAdvice.class);
    private static final String INTERNAL_SERVER_ERROR_MESSAGE = "系统异常,请联系管理员";

    /**
     * 400 - Bad Request.
     *
     * @param ex      target exception.
     * @return response.
     */
    @ExceptionHandler(value = {
            MethodArgumentNotValidException.class,
            ConstraintViolationException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Object hasTipsBadRequestException(Exception ex) {
        String message = doGetErrorMessage(ex);
        LOGGER.warn("[400:bad request] ==>> " + message, ex);
        return MessageResponse.build(message);
    }

    /**
     * 400 - Bad Request.
     *
     * @param ex      target exception.
     * @return response.
     */
    @ExceptionHandler(value = {
            BindException.class,
            ValidationException.class,
            TypeMismatchException.class,
            MissingServletRequestParameterException.class,
            MissingServletRequestPartException.class,
            HttpMessageNotReadableException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Object noTipsBadRequestException(Exception ex) {
        String message = doGetErrorMessage(ex);
        LOGGER.warn("[400:bad request] ==>> " + message, ex);
        return MessageResponse.build("请求参数错误");
    }

    /**
     * 404 - Not Found.
     *
     * @param ex      target exception.
     * @return response.
     */
    @ExceptionHandler(value = {
            NoHandlerFoundException.class
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Object notFoundException(Exception ex) {
        LOGGER.warn("[404:not found] ==>> " + ex.getMessage(), ex);
        return MessageResponse.build("不支持的请求路径");
    }

    /**
     * 405 - Method Not Allowed.
     *
     * @param ex      target exception.
     * @return response.
     */
    @ExceptionHandler(value = {
            HttpRequestMethodNotSupportedException.class
    })
    public Object methodNotAllowException(Exception ex) {
        LOGGER.warn("[405:method not allowed] ==>> " + ex.getMessage(), ex);
        return MessageResponse.build("不支持的请求类型");
    }

    /**
     * 406 - Not Acceptable.
     *
     * @param ex      target exception.
     * @return response.
     */
    @ExceptionHandler(value = {
            HttpMediaTypeNotAcceptableException.class
    })
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public Object notAcceptableException(Exception ex) {
        LOGGER.warn("[406:not acceptable] ==>> " + ex.getMessage(), ex);
        return MessageResponse.build("无法响应请求");
    }

    /**
     * 415 - Unsupported Media Type.
     *
     * @param ex      target exception.
     * @return response.
     */
    @ExceptionHandler(value = {
            HttpMediaTypeNotSupportedException.class
    })
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public Object unsupportedMediaTypeException(Exception ex) {
        LOGGER.warn("[415:unsupported media type] ==>> " + ex.getMessage(), ex);
        return MessageResponse.build("不支持的media类型");
    }

    /**
     * User Define - Business Exception.
     *
     * @param ex      target exception.
     * @return response.
     */
    @ExceptionHandler(value = {
            BusinessException.class
    })
    @ResponseStatus(HttpStatus.OK)
    public Object businessException(BusinessException ex) {
        LOGGER.warn("[" + ex.getCode() + ":business error] ==>> " + ex.getMessage(), ex);
        return Response.ok(ex.getCode(), ex.getMessage());
    }

    /**
     * 500 - Internal Server Error.
     *
     * @param ex      target exception.
     * @return response.
     */
    @ExceptionHandler(value = {
            ConversionNotSupportedException.class,
            HttpMessageNotWritableException.class,
            MissingPathVariableException.class
    })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Object serverErrorException(Exception ex) {
        LOGGER.error("[500:internal server error] ==>> " + ex.getMessage(), ex);
        return Response.err(SystemException.DEFAULT_RESPONSE_CODE, INTERNAL_SERVER_ERROR_MESSAGE);
    }

    /**
     * User Define - Server Error And Other Unknown Exception.
     *
     * @param ex      target exception.
     * @return response.
     */
    @ExceptionHandler(value = {
            SystemException.class
    })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Object systemException(SystemException ex) {
        LOGGER.error("[" + ex.getCode() + ":system error] ==>> " + ex.getMessage(), ex);
        return Response.ok(ex.getCode(), INTERNAL_SERVER_ERROR_MESSAGE);
    }

    /**
     * 401 - Unauthorized.
     * 403 - Forbidden.
     *
     * @param ex      target exception.
     * @return response.
     */
    @ExceptionHandler(value = {
            ForbiddenException.class,
            UnauthorizedException.class
    })
    public Object permissionException(ErrorCodeException ex, HttpServletResponse response) {
        int status = 0;
        if (ex instanceof UnauthorizedException) {
            LOGGER.warn("[401:unauthorized] ==>> " + ex.getMessage(), ex);
            status = HttpStatus.UNAUTHORIZED.value();
        } else if (ex instanceof ForbiddenException) {
            LOGGER.warn("[403:unauthorized] ==>> " + ex.getMessage(), ex);
            status = HttpStatus.FORBIDDEN.value();
        }

        int hardStatus = Integer.valueOf(status + "" + ex.getCode());
        response.setStatus(hardStatus);

        return Response.ok(ex.getCode(), ex.getMessage());
    }

    // ----- Hard Code -----
    /**
     * Feign exception.
     *
     * @param ex      target exception.
     * @return response.
     */
    @ExceptionHandler(value = {
            FeignException.class
    })
    public Object feignException(FeignException ex, HttpServletResponse response) {
        int hardStatus = ex.status();
        int status = Integer.valueOf(String.valueOf(hardStatus).substring(0, min(3, String.valueOf(hardStatus).length())));

        if (HttpStatus.UNAUTHORIZED.value() == status || HttpStatus.FORBIDDEN.value() == status) {
            response.setStatus(status);
            LOGGER.warn("[" + status + ":unauthorized] ==>> " + ex.getMessage(), ex);

            final String feignContent = "; content:\n";
            final String exceptionMsg = ex.getMessage();
            Response result = null;
            if (exceptionMsg != null) {
                int index = exceptionMsg.indexOf(feignContent);
                if (index != -1) {
                    String sourceResponse = exceptionMsg.substring(index + feignContent.length());
                    if (sourceResponse != null) {
                        result = JsonUtils.toClass(sourceResponse, Response.class);
                    }
                }
            }

            if (result == null) {
                String bizCode = String.valueOf(hardStatus).substring(3);
                if (bizCode != null && !bizCode.isEmpty()) {
                    int errCode = Integer.valueOf(bizCode);
                    result = Response.ok(errCode);
                }
            }

            return result == null ? new MessageResponse(exceptionMsg) : result;
        } else {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            LOGGER.error("[" + ex.status() + ":feign error] ==>> " + ex.getMessage(), ex);
            return MessageResponse.build(INTERNAL_SERVER_ERROR_MESSAGE);
        }
    }

    @ExceptionHandler(value = {
            SQLException.class
    })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Object sqlException(Exception ex) {
        LOGGER.error("[500:internal server error] ==>> " + ex.getMessage(), ex);
        return Response.err(SystemException.DEFAULT_RESPONSE_CODE, INTERNAL_SERVER_ERROR_MESSAGE);
    }

    // ----- Unknown Exception -----
    /**
     * -1 - Server Error And Other Unknown Exception.
     *
     * @param ex      target exception.
     * @return response.
     */
    @ExceptionHandler(value = {
            Exception.class
    })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Object unknownException(Exception ex) {
        LOGGER.error("[-1:unknown server error] ==>> " + ex.getMessage(), ex);
        return Response.err(SystemException.DEFAULT_RESPONSE_CODE, INTERNAL_SERVER_ERROR_MESSAGE);
    }

    private String doGetErrorMessage(Exception ex) {
        String message = ex.getMessage();

        if (ex instanceof MethodArgumentNotValidException) {
            BindingResult result = ((MethodArgumentNotValidException) ex).getBindingResult();
            StringBuilder messageBuilder = new StringBuilder();
            for (FieldError error : result.getFieldErrors()) {
                messageBuilder
                        .append("[")
                        .append(error.getField())
                        .append(":")
                        .append(error.getDefaultMessage())
                        .append("]");
            }
            message = messageBuilder.toString();
        }

        return message;
    }

    private int min(int i, int j) {
        return i < j ? i : j;
    }
}