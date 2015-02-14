# VibeProject
There's the first aim of this project create a telestrator android app prototype.

---------------
###Notation Rules

#### Kısaltmalar
 * View Widget id isimleri, önce değişken ismi sonra widget kısaltması şeklinde,
     Örnek, "variableName_bt", "variableName_tv"
        * bt --> button, tv --> textview

 * XML file içersinde, önce attribute_name_, sonra widget kısaltması.
        Örnek, *<dimen name="padding_size_bt">24dp</dimen>

 * Private memberlar, önce member olduğunu belirtmek için m, sonra değişken ismi, en son
        widget kısaltması şeklindedir.
        Örnek, "mFileChooser_bt", "mTitle_et"


###Dökümantasyon ve Notlar

**Not:** __extends__ **View** şeklinde oluşturulan class lar, **XML** dosyasında, package name ile
  gösterilebilmektedir. Buradan **android:width** veya **android:height** ayarlayabiliriz.

###TODO

    [-] Yeni bir CustomView class oluşturulacak, View dan türeyecek.
    [-] CustomView test işlemi ve entegrasyonu.