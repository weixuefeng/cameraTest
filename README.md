# cameraTest

> [参考](https://developer.android.com/guide/topics/media/camera)

## 流程

1. 权限配置， AndroidManifest.xml 配置

```
 <uses-permission android:name="android.permission.CAMERA"/>

    <uses-feature android:name="android.hardware.camera"
        android:required="true" />
<!-- todo: 待研究具体情况 -->
    <queries>
        <!-- Browser -->
        <intent>
            <action android:name="android.intent.action.VIEW" />
            <data android:scheme="http" />
        </intent>
        <!-- Camera -->
        <intent>
            <action android:name="android.media.action.IMAGE_CAPTURE" />
        </intent>
        <!-- Gallery -->
        <intent>
            <action android:name="android.intent.action.GET_CONTENT" />
        </intent>
    </queries>
```

2. 动态请求权限
