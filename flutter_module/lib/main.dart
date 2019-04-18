import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter_module/common/net/NetWork.dart';
import 'pages/HomePage.dart';
import 'pages/MinePage.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';

void main() => runApp(AppEntry());

class AppEntry extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    // TODO: implement createState
    return new MyAppState();
  }
}

class MyAppState extends State<AppEntry> {
  // This widget is the root of your application.
  var _body;
  var _tabIndex = 0;
  void _initData() {
    _body = new IndexedStack(
      children: <Widget>[
        new MyHomePage(),
        new MinePage(),
      ],
      index: _tabIndex,
    );
  }

  @override
  Widget build(BuildContext context) {
    _initData();
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: new Scaffold(
          body: _body,
          bottomNavigationBar: Padding(
            padding: EdgeInsets.all(0),
            child: new CupertinoTabBar(
              items: <BottomNavigationBarItem>[
                new BottomNavigationBarItem(
                  title: Padding(
                    child: Text("首页"),
                    padding: EdgeInsets.fromLTRB(0, 0, 0, 0),
                  ),
                  icon: Padding(
                    child: Image.asset(_tabIndex == 0
                        ? "assets/images/ic_nav_news_actived.png"
                        : "assets/images/ic_nav_news_normal.png"),
                    padding: EdgeInsets.fromLTRB(0, 10, 0, 0),
                  ),
                ),
                new BottomNavigationBarItem(
                  title: Padding(
                    child: Text("我的"),
                    padding: EdgeInsets.fromLTRB(0, 0, 0, 0),
                  ),
                  icon: Padding(
                    child: Image.asset(_tabIndex == 1
                        ? "assets/images/ic_nav_my_pressed.png"
                        : "assets/images/ic_nav_my_normal.png"),
                    padding: EdgeInsets.fromLTRB(0, 10, 0, 0),
                  ),
                ),
              ],
              currentIndex: _tabIndex,
              onTap: (index) {
                setState(() {
                  _tabIndex = index;
                });
              },
            ),
          )),
      routes: <String, WidgetBuilder>{
        "/mine": (BuildContext context) => MinePage()
      },
    );
  }
}
