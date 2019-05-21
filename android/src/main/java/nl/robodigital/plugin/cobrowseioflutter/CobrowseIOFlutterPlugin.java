package nl.robodigital.plugin.cobrowseioflutter;

import android.app.Activity;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

import io.cobrowse.core.CobrowseIO;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.HashMap;


/**
 * CobrowseIOFlutterPlugin
 */
public class CobrowseIOFlutterPlugin implements MethodCallHandler {

  private final Activity activity;

  private CobrowseIOFlutterPlugin(Activity activity) {
    this.activity = activity;
  }

  /**
   * Plugin registration.
   */
  public static void registerWith(Registrar registrar) {
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "cobrowseio_plugin");
    channel.setMethodCallHandler(new CobrowseIOFlutterPlugin(registrar.activity()));
  }

  @Override
  public void onMethodCall(MethodCall call, Result result) {
    try {
      switch (call.method) {
        case "start":
          handlerStart(call, result);
          break;
        default:
          result.notImplemented();
          break;
      }
    } catch (Exception e) {
      result.error("Error", e.getMessage(), e.getStackTrace());
    }
  }

  public void handlerStart(MethodCall call, Result result) throws Exception {
    try {
      String licenseKey = call.argument("licenseKey").toString();
      HashMap<String, Object> customData = call.argument("customData");
      CobrowseIO.instance().license(licenseKey);
      CobrowseIO.instance().customData(customData);
      CobrowseIO.instance().start(this.activity);
      result.success("Started!");
    } catch (Exception ex) {
      result.error("Error", ex.getMessage(), ex.getStackTrace());
    }
  }
}
