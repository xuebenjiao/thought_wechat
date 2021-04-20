package com.xbj.network.converter;

import com.thoughtwork.base.BaseApplication;
import com.thoughtwork.base.utils.ToastUtil;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Time :2020/11/19
 * Author:xbj
 * Description :处理服务端无返回数据或者NULL的情况
 */
public class NullOnEmptyConverterFactory extends Converter.Factory {

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        final Converter<ResponseBody, ?> delegate = retrofit.nextResponseBodyConverter(this, type, annotations);
        return new Converter<ResponseBody,Object>() {
            @Override
            public Object convert(ResponseBody body) throws IOException {
                if (body.contentLength() == 0){
         /*           JsonObject object =  new JsonObject();
                    object.addProperty("msg","服务端无响应");
                    return object.toString();*/
                    ToastUtil.show(BaseApplication.getmContext(),"服务端无响应");
                    return  null;

                }
                return delegate.convert(body);
            }
        };
    }
}
