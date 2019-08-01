import 'dart:async';

import 'package:flutter/services.dart';

class RwTts {
  static MethodChannel _channel =
      MethodChannel('com.rockwellits.rw_plugins/rw_tts');

  /// Speaks given text
  ///
  /// @param text Text to be pronounced
  /// @param requestCode Request code
  /// @return Request code
  static Future<int> speak(String text, {int requestCode = 0}) async {
    return await _channel.invokeMethod('speak', {
      'text': text,
      'requestCode': requestCode,
    });
  }
}
