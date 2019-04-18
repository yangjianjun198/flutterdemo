import 'package:flutter/material.dart';
import '../common/NativeImageProvider.dart';

class MinePage extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new _MinePageState();
  }
}

class _MinePageState extends State<MinePage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title: Text("我的"),
        ),
        body: Center(
            child: new Image(
          image: new NativeImageProvider("ic_launcher_round.png"),
        )) // This trailing comma makes auto-formatting nicer for build methods.
        );
  }
}
