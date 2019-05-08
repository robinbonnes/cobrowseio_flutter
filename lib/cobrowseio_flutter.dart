import 'dart:async';
import 'dart:io';

import 'package:flutter/services.dart';

class CobrowseIO {

  static const MethodChannel _channel = const MethodChannel('cobrowseio_plugin');

  static Future<String> start(String licenseKey) async {

    final Map<String, dynamic> params = <String, dynamic>{
      "licenseKey": licenseKey
    };

    String response = await _channel.invokeMethod('start', params);
    
    return response;
  }
}
