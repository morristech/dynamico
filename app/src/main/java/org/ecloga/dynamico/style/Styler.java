package org.ecloga.dynamico.style;

import android.view.View;
import org.json.JSONObject;

public interface Styler {

    View style(View view, JSONObject attributes) throws Exception;
}