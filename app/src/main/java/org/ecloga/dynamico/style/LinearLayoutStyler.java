package org.ecloga.dynamico.style;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import org.ecloga.dynamico.ViewFactory;
import org.json.JSONObject;

public class LinearLayoutStyler extends DefaultStyler {

    public LinearLayoutStyler(ViewFactory factory, Context context) {
        super(factory, context);
    }

    @Override
    public View style(View view, JSONObject attributes) throws Exception {
        super.style(view, attributes);

        LinearLayout linearLayout = (LinearLayout) view;

        if(attributes.has("orientation")) {
            String orientation = attributes.getString("orientation");

            if(orientation.equalsIgnoreCase("vertical")) {
                linearLayout.setOrientation(LinearLayout.VERTICAL);
            }else if(orientation.equalsIgnoreCase("horizontal")) {
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            }
        }

        return linearLayout;
    }
}