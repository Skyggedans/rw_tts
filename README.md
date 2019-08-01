# rw_tts

TTS plugin for RealWear HMT-1(Z1) 

## Usage
```dart
import 'package:rw_tts/rw_tts.dart';

// Set requestCode to less than 1 if you don't need a callback
RwTts.speak('RealWear is awesome!', requestCode: 100).then((requestCode) {
    print('TTS request $requestCode finished');
});
```