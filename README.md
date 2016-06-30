# React Native Image Android

给 React Native Image Android 版本的 noLoad事件中加入原生图片的宽高值

## Setup

First, install the package:
```
npm i -S git+ssh://git@github.com:bylevel/react-native-image-android.git
```

Then, follow those instructions:

### Android

#### Update your gradle files

```gradle
// android/settings.gradle
...

include ':react-native-image-android'
project(':react-native-image-android').projectDir = new File(rootProject.projectDir, '../node_modules/react-native-image-android/android')
```

```gradle
// android/app/build.gradle
...
    compile project(':RNDeviceInfo')
    compile project(':react-native-toast')
    compile project(":react-native-image-rotation")
    compile project(":react-native-image-android")  // <-- 插入这里
```

```java
// android/app/src/main/java/com/shuline/MainActivity.java
...
import com.learnium.RNDeviceInfo.*;
import com.remobile.toast.*;
import cn.chronos.rnimagerotation.ImageRotationPackage;
import cn.chronos.image.ImageAndroidPackage;  // <-- 这里
...
        new VectorIconsPackage(),
        new MainReactPackage(),
        new ImageRotationPackage(),
        new ImageAndroidPackage(),  // <-- 这里
        mImagePicker);
...
```


## Usage example

```javascript
import ImageAndroid from 'react-native-image-android';
export default class NetImage extends Component {
    _onLoad( evt ) {
        const {onLoad} = this.props;
        if ( evt.nativeEvent.width ) {
            console.log(evt.nativeEvent);
        }

        onLoad && onLoad( evt );
    }

    render() {
        return <ImageAndroid{...this.props}
                            onLoad={ this._onLoad.bind( this ) } />;
    }
}
```
