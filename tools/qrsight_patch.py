from pathlib import Path

MAIN = Path("qrscanner/app/src/main/java/com/jerry/qrsight/MainActivity.java")
HISTORY = Path("qrscanner/app/src/main/java/com/jerry/qrsight/HistoryActivity.java")

main = MAIN.read_text(encoding="utf-8")
main = main.replace(
    "import android.content.ColorStateList;",
    "import android.content.res.ColorStateList;",
)
if "import androidx.camera.core.ExperimentalGetImage;" not in main:
    main = main.replace(
        "import androidx.camera.core.CameraSelector;\n",
        "import androidx.camera.core.CameraSelector;\nimport androidx.camera.core.ExperimentalGetImage;\n",
    )
if "    @ExperimentalGetImage\n    private void analyzeImage" not in main:
    main = main.replace(
        "    private void analyzeImage(@NonNull ImageProxy imageProxy) {",
        "    @ExperimentalGetImage\n    private void analyzeImage(@NonNull ImageProxy imageProxy) {",
    )
main = main.replace(
    """        Collections.sort(found, Comparator
                .comparingDouble((Detection item) -> item.bounds.top)
                .thenComparingDouble(item -> item.bounds.left));""",
    """        Collections.sort(found, new Comparator<Detection>() {
            @Override
            public int compare(Detection first, Detection second) {
                int top = Float.compare(first.bounds.top, second.bounds.top);
                return top != 0 ? top : Float.compare(first.bounds.left, second.bounds.left);
            }
        });""",
)
MAIN.write_text(main, encoding="utf-8")

history = HISTORY.read_text(encoding="utf-8").replace(
    "import android.content.ColorStateList;",
    "import android.content.res.ColorStateList;",
)
HISTORY.write_text(history, encoding="utf-8")
