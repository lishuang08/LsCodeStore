import 'dart:collection';
import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;

import 'data.dart';

import 'package:sqflite/sqflite.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        // This is the theme of your application.
        //
        // Try running your application with "flutter run". You'll see the
        // application has a blue toolbar. Then, without quitting the app, try
        // changing the primarySwatch below to Colors.green and then invoke
        // "hot reload" (press "r" in the console where you ran "flutter run",
        // or simply save your changes to "hot reload" in a Flutter IDE).
        // Notice that the counter didn't reset back to zero; the application
        // is not restarted.
        primarySwatch: Colors.blue,
        // This makes the visual density adapt to the platform that you run
        // the app on. For desktop platforms, the controls will be smaller and
        // closer together (more dense) than on mobile platforms.
        visualDensity: VisualDensity.adaptivePlatformDensity,
      ),
      home: MyHomePage(title: 'Flutter Demo Home Page'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  MyHomePage({Key key, this.title}) : super(key: key);

  // This widget is the home page of your application. It is stateful, meaning
  // that it has a State object (defined below) that contains fields that affect
  // how it looks.

  // This class is the configuration for the state. It holds the values (in this
  // case the title) provided by the parent (in this case the App widget) and
  // used by the build method of the State. Fields in a Widget subclass are
  // always marked "final".

  final String title;

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  List<Specice> widgets = [];
  int _counter = 0;

  var aaa = new HashMap();
  var bbb = [1, 2, 3, 4];
  var ccc = {0: "0", 1: "1", 2: '2'};

  @override
  void initState() {
    super.initState();
    _loadData();
  }

  void _fabClick() {
    setState(() {
      // This call to setState tells the Flutter framework that something has
      // changed in this State, which causes it to rerun the build method below
      // so that the display can reflect the updated values. If we changed
      // _counter without calling setState(), then the build method would not be
      // called again, and so nothing would appear to happen.
      _counter++;

      sayHello(_counter);
      show();
      dowohat();
    });
  }

  int a = 2;
  int b = 3;

  show() => debugPrint('${a ~/ b}'); //取整，0

  sayHello(name) {
    var a;
    var b = false;
    var c = b ? "a" : "b";
    var input = new Runes(
        '\u2665  \u{1f605}  \u{1f60e}  \u{1f47b}  \u{1f596}  \u{1f44d}');
    debugPrint('Hello $name!' + new String.fromCharCodes(input));
    debugPrint(c);
    debugPrint(a ?? "null ");
    debugPrint(a == c ? "b==c" : "b!=c");
  }

  dowohat() {
    var s = 'string interpolation';

    assert('Dart has $s, which is very handy.' ==
        'Dart has string interpolation, ' + 'which is very handy.');
    assert('That deserves all caps. ' + '${s.toUpperCase()} is very handy!' ==
        'That deserves all caps. ' + 'STRING INTERPOLATION is very handy!');
  }

  showLoadingDialog() {
    return widgets.length == 0;
  }

  getProgressDialog() {
    return new Center(child: new CircularProgressIndicator());
  }

  getBody() {
    if (showLoadingDialog()) {
      return getProgressDialog();
    } else {
      return _getList();
    }
  }

  @override
  Widget build(BuildContext context) {
    return new Scaffold(
      appBar: new AppBar(
        title: new Text(widget.title),
      ),
      body: getBody(),

      floatingActionButton: new FloatingActionButton(
        onPressed: _fabClick,
        tooltip: 'Increment',
        child: new Icon(Icons.add),
      ), // This trailing comma makes auto-formatting nicer for build methods.
    );
  }

  Widget getRow(int i) {
    return new Padding(
        padding: new EdgeInsets.all(10.0),
        child: new Text("Row ${widgets[i].key}  ${widgets[i].nameKey}"));
  }

  _getList() {
    return new ListView.builder(
        itemCount: widgets.length,
        itemBuilder: (BuildContext context, int position) {
          return getRow(position);
        });
  }

  _loadData() async {
    String dataURL = "http://api.gbif.org/v1/species";
    http.Response response = await http.get(dataURL);
    setState(() {
      Map json = jsonDecode(response.body.toString());

      var sp = new SpeciceBack.fromJson(json);

      widgets = sp.results;

      widgets.forEach((key) {
        debugPrint("widgets    ${key.toString()} ");
      });
    });
  }

  _insertDataBasr() async {
    var db = await openDatabase('my_db.db');
  }
}
