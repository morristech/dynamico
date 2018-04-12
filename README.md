[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![](https://jitpack.io/v/jelic98/dashbug.svg)](https://jitpack.io/#jelic98/dashbug)

# Dynamico

Android library for inflating dynamic layouts in runtime based on JSON configuration fetched from server. Useful in situations when layouts need to change without updating the app.

## Installing

1. Add repository in root ```build.gradle```

```gradle
    allprojects {
        repositories {
            ...
            maven { url 'https://jitpack.io' }
        }
    }
```

2. Add the dependency

```gradle
    dependencies {
    	compile 'com.github.jelic98:dynamico:1.0.1'
	}
```

## Usage

1. Create JSON layout and upload it somewhere

```json
{
	"views": [
		{
          	"class": "android.widget.TextView",
          	"attributes": {
				"layout_width": "match_parent",
				"layout_height": "wrap_content",
				"layout_margin": "50dp",
				"paddingTop": "15dp",
				"backgroundColor": "#2980b9",
				"text": "This is working!",
				"textSize": "16sp",
				"textColor": "#ffba00"
			}
		}
	]
}
```

2. Create XML layout

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
	xmlns:android="http://schemas.android.com/apk/res/android"
	android:layout_width="match_parent"
	android:layout_height="match_parent"
	android:orientation="vertical"
	android:id="@+id/mainLayout">
		
	<!-- Dynamic content will be added here -->
</LinearLayout>
```

3. Initialize Dynamico by passing it:
* URL = link to **directory** of uploaded JSON layout
* name = name of uploaded JSON layout **file**
* id = wrapper layout identifier

```java
new Dynamico("http://ecloga.org/dynamico",
	"activity_main",
	findViewById(R.id.mainLayout))
    .initialize();
```

## Additional features

* Listener for layout inflation state

```java
setLayoutStateListener(new LayoutStateListener() {
	@Override
	public void onSuccess(String message) {
		// everything is okay
	}
	
	@Override
	public void onError(String message) {
		// notify user
	}
});
```

* Force cache loading

```java
onlyCache(true);
```

## TODO

* Use device hash to target specific users
* Cache ImageView source images

### Still pretty fresh. Stay tuned!
