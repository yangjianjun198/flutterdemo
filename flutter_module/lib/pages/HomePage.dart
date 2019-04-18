import 'package:flutter/material.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';

class MyHomePage extends StatefulWidget {
  MyHomePage({Key key, this.title}) : super(key: key);
  final String title;

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  String sharedData = "noData";
  List<String> items = new List<String>.generate(10, (i) => "Item $i");
  ListView _getListView() {
    return new ListView.builder(
        itemCount: items.length,
        itemBuilder: (context, index) {
          return getRow(index);
        });
  }

  Widget getRow(int pos) {
    return Container(
        width: ScreenUtil.getInstance().setWidth(750),
        height: ScreenUtil.getInstance().setHeight(750),
        child: Padding(
            padding: EdgeInsets.all(10),
            child: RaisedButton(
                child: Text("第$pos个"),
                onPressed: () {
                  Navigator.pushNamed(context, '/mine');
                })));
  }

  @override
  Widget build(BuildContext context) {
    ScreenUtil.instance = ScreenUtil(width: 750, height: 1334)..init(context);
    return Scaffold(
      appBar: AppBar(
        title: Text("首页"),
      ),
      body: _getListView(),
    );
  }
}
