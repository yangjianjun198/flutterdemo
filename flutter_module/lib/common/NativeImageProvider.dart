import 'dart:io';
import 'dart:typed_data';
import 'dart:ui' as ui show Codec;
import 'dart:ui' as ui show instantiateImageCodec;
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

/// 自定义ImageProvider用于读取原生资源中的图片
class NativeImageProvider extends ImageProvider<NativeImageProvider> {
  /// 需要从原生中去加载的图片名格式如： icon_back.png / icon_new.jpg
  ///
  static const MethodChannel _channel =
      const MethodChannel('plugins.flutter.yjj/native_image_getter');

  final String imageName;

  /// Scale of the image
  final double scale;

  const NativeImageProvider(this.imageName, {this.scale: 1.0});

  @override
  ImageStreamCompleter load(key) {
    return new MultiFrameImageStreamCompleter(
        codec: _loadAsync(key),
        scale: key.scale,
        informationCollector: (StringBuffer information) {
          information.writeln('Image provider: $this');
          information.write('Image key: $key');
        });
  }

  Future<ui.Codec> _loadAsync(NativeImageProvider key) async {
    Directory directory = await getNativeImage(imageName);
    if (directory.path.isEmpty) {
      // 如果图片不存在于原生的话 那么读取images下的图片
      AssetBundle assetBundle = PlatformAssetBundle();
      ByteData byteData = await assetBundle.load("assets/images/$imageName");
      return await PaintingBinding.instance
          .instantiateImageCodec(byteData.buffer.asUint8List());
    }
    var file = File(directory.path);
    return await _loadAsyncFromFile(key, file);
  }

  Future<ui.Codec> _loadAsyncFromFile(
      NativeImageProvider key, File file) async {
    assert(key == this);

    final Uint8List bytes = await file.readAsBytes();

    if (bytes.lengthInBytes == 0) {
      throw new Exception("File was empty");
    }
    return await ui.instantiateImageCodec(bytes);
  }

  @override
  Future<NativeImageProvider> obtainKey(ImageConfiguration configuration) {
    return new SynchronousFuture<NativeImageProvider>(this);
  }

  /// 自定义读取原生图片的Channel
  /// 读取对应的原生图片
  Future<Directory> getNativeImage(String imageName) async {
    String path = "";
    try {
      path = await _channel.invokeMethod('getImage', imageName);
    } on MissingPluginException catch (ignore) {}
    return new Directory(path);
  }
}
