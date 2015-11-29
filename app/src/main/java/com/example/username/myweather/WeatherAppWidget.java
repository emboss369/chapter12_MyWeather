package com.example.username.myweather;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.RemoteViews;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link WeatherAppWidgetConfigureActivity WeatherAppWidgetConfigureActivity}
 */
public class WeatherAppWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        String widgetText = WeatherAppWidgetConfigureActivity.loadTitlePref(context, appWidgetId);
        getJsonAndShowDisplay(widgetText, context, appWidgetManager, appWidgetId);
//        // Construct the RemoteViews object
//        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.weather_app_widget);
//        views.setTextViewText(R.id.appwidget_text, widgetText);
//
//        // Instruct the widget manager to update the widget
//        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
    static private void getJsonAndShowDisplay(String cityID,
                                              final Context context,
                                              final AppWidgetManager appWidgetManager,
                                              final int appWidgetId) {
        String url = "http://weather.livedoor.com/forecast/webservice/json/v1?city=" + cityID;

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET,
                        url,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    String title = response.getString("title");
                                    String telop = response.getJSONArray("forecasts")
                                            .getJSONObject(0)
                                            .getString("telop");
                                    String url = response
                                            .getJSONArray("forecasts")
                                            .getJSONObject(0)
                                            .getJSONObject("image")
                                            .getString("url");

                                    final RemoteViews views
                                            = new RemoteViews(context.getPackageName(),
                                            R.layout.weather_app_widget);
                                    views.setTextViewText(R.id.title, title);
                                    views.setTextViewText(R.id.telop, telop);
                                    appWidgetManager.updateAppWidget(appWidgetId, views);

                                    ImageLoader imageLoader
                                            = MySingleton.getInstance(context).getImageLoader();

                                    imageLoader.get(url, new ImageLoader.ImageListener() {
                                        @Override
                                        public void onResponse(ImageLoader.ImageContainer response,
                                                               boolean isImmediate) {
                                            Bitmap b = response.getBitmap();
                                            if (b == null) return;
                                            Bitmap bc = b.copy(Bitmap.Config.ARGB_8888, true);
                                            views.setImageViewBitmap(R.id.imageView, bc);
                                            appWidgetManager.updateAppWidget(appWidgetId, views);
                                        }

                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Log.d("ImageListener", error.toString());
                                        }
                                    });

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("WeatherAppWidget", error.toString());
                            }
                        }
                );
        MySingleton.getInstance(context).addToRequestQueue(jsObjRequest);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
        for (int appWidgetId : appWidgetIds) {
            WeatherAppWidgetConfigureActivity.deleteTitlePref(context, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

