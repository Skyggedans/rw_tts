import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:rw_tts/rw_tts.dart';

void main() {
  const MethodChannel channel = MethodChannel('rw_tts');

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
  });
}
