import 'package:flutter/material.dart';

import 'package:rw_tts/rw_tts.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('RealWear HMT-1 TTS example app'),
        ),
        body: Builder(builder: (BuildContext context) {
          return Center(
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: <Widget>[
                TextFormField(
                  onFieldSubmitted: (value) {
                    RwTts.speak(value, requestCode: 100).then((requestCode) {
                      showDialog(
                        context: context,
                        builder: (BuildContext context) {
                          return AlertDialog(
                            title: Text(''),
                            content: Text('TTS request $requestCode finished'),
                            actions: <Widget>[
                              new FlatButton(
                                child: const Text('OK'),
                                onPressed: () {
                                  Navigator.of(context).pop();
                                },
                              ),
                            ],
                          );
                        },
                      );
                    });
                  },
                ),
              ],
            ),
          );
        }),
      ),
    );
  }
}
