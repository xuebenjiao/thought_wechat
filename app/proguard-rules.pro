# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

#申明第三方jar包

-keep class com.huantansheng.easyphotos.models.** { *; }
-libraryjars ../base/libs/com.baidu.tts_2.3.2.20180713_6101c2a.jar
-libraryjars ../base/libs/eventbus-latest.jar
-libraryjars ../base/libs/VoiceRecognition-2.1.20.jar
-libraryjars ../lib-zxing/libs/core_3.0.1.jar


#工业机驱动
-libraryjars ../drive-module/libs/android-zltd-v1.jar
-libraryjars ../drive-module/libs/telephoneimpl.jar
-libraryjars ../drive-module/libs/zltd_common.jar
# 工业机扫描类相关
-keep class com.zltd.** { *; }

#微信支付
-keep class com.tencent.mm.opensdk.** {
    *;
}

-keep class com.tencent.wxop.** {
    *;
}

-keep class com.tencent.mm.sdk.** {
    *;
}




#EventBus混淆
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}


-dontwarn android.databinding.**
-keep class android.databinding.** { *; }

-keepclassmembers class * extends org.greenrobot.greendao.AbstractDao {
public static java.lang.String TABLENAME;
}
-keep class **$Properties {*;}

# If you do not use SQLCipher:
-dontwarn net.sqlcipher.database.**
# If you do not use RxJava:
-dontwarn rx.**

# glide 的混淆代码
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
# for DexGuard only
#-keepresourcexmlelements manifest/application/meta-data@value=GlideModule

# banner 的混淆代码
-keep class com.youth.banner.** {
    *;
 }
 # AndroidEventBus 混淆
 -keep class org.simple.** { *; }
 -keep interface org.simple.** { *; }
 -keepclassmembers class * {
     @org.simple.eventbus.Subscriber <methods>;
 }
 -keepattributes *Annotation*

 #百度语音
 -keep class com.baidu.speech.**{*;}


 ##---------------Begin: proguard configuration for Gson  ----------
 # Gson uses generic type information stored in a class file when working with fields. Proguard
 # removes such information by default, so configure it to keep all of it.
 -keepattributes Signature

 # For using GSON @Expose annotation
 -keepattributes *Annotation*

 # Gson specific classes
 -dontwarn sun.misc.**
 #-keep class com.google.gson.stream.** { *; }

 # Application classes that will be serialized/deserialized over Gson
  -keep class sun.misc.Unsafe { *; }
 -keep class com.xbj.comment.entity.** {*;}
 -keep class net.sourceforge.pinyin4j.** {*;}
 -keep class com.xbj.comment.model.** {*;}
 -keep class com.xbj.scan.requestentity.** {*;}
 -keep class com.xbj.scan.databind.entity.** {*;}
 -keep class com.xbj.scan.databind.pageentity.** {*;}
 -keep class com.xbj.scan.databind.model.** {*;}
 -keep class com.xbj.scan.api.bean.** {*;}
 -keep class com.xbj.scan.databind.viewmodel.** {*;}
 -keep class com.xbj.common.entity.** {*;}
 -keep class com.xbj.base.model.** {*;}
 -keep class com.uuzuche.lib_zxing.entity.** {*;}
 -keep class com.xbj.common.notice.api.requestparameter.** {*;}
 -keep class com.xbj.common.notice.entity.** {*;}
 -keep class com.xbj.common.bean.** {*;}
 #-keep class com.yto.selectcity.** {*;}
 -keep class com.xbj.locker.pageentity.** {*;}
 -keep class com.xbj.locker.request.** {*;}
 -keep class com.xbj.locker.viewmodel.** {*;}

#数据库实体对象
 -keep class com.xbj.mode.** {*;}
 -keep class com.xbj.greendao.gen.** {*;}
 -keep class com.xbj.network.common.api.bean.** {*;}

 -keep class com.xbj.usercenter.model.** {*;}
 -keep class com.xbj.usercenter.entity.** {*;}
 -keep class com.xbj.webview.entity.** {*;}


 # Prevent proguard from stripping interface information from TypeAdapterFactory,
 # JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
 -keep class * implements com.google.gson.TypeAdapterFactory
 -keep class * implements com.google.gson.JsonSerializer
 -keep class * implements com.google.gson.JsonDeserializer

 # Prevent R8 from leaving Data object members always null
 -keepclassmembers,allowobfuscation class * {
   @com.google.gson.annotations.SerializedName <fields>;
 }

 ##---------------End: proguard configuration for Gson  ----------


#okhttp框架的混淆
-dontwarn com.squareup.okhttp.internal.http.*
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-keepnames class com.levelup.http.okhttp.** { *; }
-keepnames interface com.levelup.http.okhttp.** { *; }
-keepnames class com.squareup.okhttp.** { *; }
-keepnames interface com.squareup.okhttp.** { *; }
-dontwarn okhttp3.**
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-keepnames class okhttp3.** { *; }
-keepnames interface okhttp3.** { *; }



#androidx混淆
-keep class com.google.android.material.** {*;}
-keep class androidx.** {*;}
-keep public class * extends androidx.**
-keep interface androidx.** {*;}
-dontwarn com.google.android.material.**
-dontnote com.google.android.material.**
-keep class * implements androidx.startup.**
-dontwarn androidx.**


#枚举
-keepclassmembers enum * {
    **[] $VALUES;
    public *;
}

-keep class **.R$* {*;}
-ignorewarnings


#忽略百度
-dontwarn com.baidu.**
-keep class com.baidu.** {*;}


# androideventbus
#-keep class org.simple.** { *; }
#-keep interface org.simple.** { *; }
#-keepclassmembers class * {
#    @org.simple.eventbus.Subscriber <methods>;
#}
#-keepattributes *Annotation*


#zxing
-dontwarn com.google.zxing.**
-keep class com.google.zxing.** { *; }


#指定代码的压缩级别
    		-optimizationpasses 5

    		#包明不混合大小写
    		-dontusemixedcaseclassnames

    		#不去忽略非公共的库类
    		-dontskipnonpubliclibraryclasses

    		 #优化  不优化输入的类文件
    		-dontoptimize

    		 #不做预校验
    		-dontpreverify

    		 #混淆时是否记录日志
    		-verbose

    		 # 混淆时所采用的算法
    		-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

    		#保护注解
    		-keepattributes *Annotation*
    		#内部类
            -keepattributes InnerClasses
    		# 保持哪些类不被混淆
    		-keep public class * extends android.app.Fragment
    		-keep public class * extends android.app.Activity
    		-keep public class * extends android.app.Application
    		-keep public class * extends android.app.Service
    		-keep public class * extends android.content.BroadcastReceiver
    		-keep public class * extends android.content.ContentProvider
    		-keep public class * extends android.app.backup.BackupAgentHelper
    		-keep public class * extends android.preference.Preference
    		-keep public class com.android.vending.licensing.ILicensingService
    		#如果有引用v4包可以添加下面这行
    		-keep public class * extends android.support.v4.app.Fragment
    		-keep public class * extends android.support.v4.app.FragmentActivity
    		-keep public class * extends android.support.v7.widget
    		-keep class org.xmlpull.v1.** {*;}
            -keep class android.util.Xml.** {*;}
            #Dialog 按钮监听事件（内部类 DialogInterface.OnClickListener）
            -keep class android.content.**{*;}
    		##记录生成的日志数据,gradle build时在本项目根目录输出##
    		#apk 包内所有 class 的内部结构
    		-dump class_files.txt
    		#未混淆的类和成员
    		-printseeds seeds.txt
    		#列出从 apk 中删除的代码
    		-printusage unused.txt
    		#混淆前后的映射
    		-printmapping mapping.txt
    		#描述apk内所有class文件的内部结构。
    		#-printdump dump.txt
    		#忽略警告
    		-dontwarn com.yto.comment.**
    		-dontwarn okio.**
    		#如果引用了v4或者v7包
    		-dontwarn android.support.**
    		#自己写的自定义控件不要混淆
            -keep public class * extends android.view.View { *; }

            #百度地图
             -keep class com.baidu.**{*;}
             -keep class vi.com.**{*;}
             -dontwarn com.baidu.**
   		   -keepclasseswithmembernames class * {
    		    native <methods>;
    		}
    		# Keep names - Native method names. Keep all native class/method names.
            -keepclasseswithmembers,allowshrinking class * {
                native <methods>;
            }

    		#保持自定义控件类不被混淆
    		-keepclasseswithmembers class * {
    		    public <init>(android.content.Context, android.util.AttributeSet);
    		}

    		#保持自定义控件类不被混淆
    		-keepclassmembers class * extends android.app.Activity {
    		   public void *(android.view.View);
    		}

    		#保持自定义控件类不被混淆
    		-keepclassmembers class * extends android.support.v4.app.FragmentActivity {
    		   public void *(android.view.View);
    		}
    		#保持自定义控件类不被混淆
             -keepclassmembers class * extends android.support.v4.app.Fragment {
              public void *(android.view.View);
             }

    		#保持 Parcelable 不被混淆
    		-keep class * implements android.os.Parcelable {
    		  public static final android.os.Parcelable$Creator *;
    		}

    		#保持 Serializable 不被混淆
    		-keepnames class * implements java.io.Serializable

    		#保持 Serializable 不被混淆并且enum 类也不被混淆
    		-keepclassmembers class * implements java.io.Serializable {
    		    static final long serialVersionUID;
    		    private static final java.io.ObjectStreamField[] serialPersistentFields;
    		    !static !transient <fields>;
    		    !private <fields>;
    		    !private <methods>;
    		    private void writeObject(java.io.ObjectOutputStream);
    		    private void readObject(java.io.ObjectInputStream);
    		    java.lang.Object writeReplace();
    		    java.lang.Object readResolve();
    		}

    		#保持枚举 enum 类不被混淆 如果混淆报错，建议直接使用上面的 -keepclassmembers class * implements java.io.Serializable即可
    		-keepclassmembers enum * {
    		  public static **[] values();
    		  public static ** valueOf(java.lang.String);
    		}

    		-keepclassmembers class * {
    		    public void *ButtonClicked(android.view.View);
    		}

    		#不混淆资源类
    		-keepclassmembers class **.R$* {
    		    public static <fields>;
    		}
#bugly混淆
    		-dontwarn com.tencent.bugly.**
            -keep public class com.tencent.bugly.**{*;}
            -keep class android.support.**{*;}

           #日期选择混淆
            -keepattributes InnerClasses,Signature
            -keepattributes *Annotation*

            -keep class cn.qqtheme.framework.entity.** { *;}
