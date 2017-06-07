package com.songwenju.androidtvstudy.data;

import com.songwenju.androidtvstudy.model.Movie;

import java.util.ArrayList;

public class MovieProvider {


    private static ArrayList<Movie> mItems = null;

    private MovieProvider() {}

    public static ArrayList<Movie> getMovieItems() {
        String description = "Lorem ipsum dolor sit amet, qui mundi vivendum cu. Mazim dicant possit te his. Quo solet dicant prodesset eu, pri deseruisse concludaturque ea, saepe maiorum sea et. Impetus discere sed at. Vim eu novum erant integre, te tale voluptatibus est. Facer labores te mel.\n" +
                "\n" +
                "Dictas denique qualisque mea id, cu mei verear fabellas. Mel no autem nusquam, viderer oblique te mei. At minimum corpora consulatu vim. Cibo nominavi vis no, in verterem vulputate eos, essent iriure cu vel. Ius ferri expetendis ad, omnes aeterno nominati id his, eum debitis lobortis comprehensam id.\n" +
                "\n" +
                "Illud dicit nostrud sit no. Eu quod nostro pro. Ut gubergren mnesarchum has, nostro detracto scriptorem et quo, no illud phaedrum recteque sea. Ad his summo probatus recusabo. Qui amet tale viris et, ei his quodsi torquatos adipiscing. Laudem malorum no eum, accusam mandamus sit ex, est ut tractatos dissentiet. Dictas feugiat usu et, an his cibo appareat placerat, eu quis dignissim qui.\n" +
                "\n" +
                "Euripidis neglegentur eu per, denique singulis vel cu, malis dolore ne duo. Cum no iracundia persecuti expetendis. Vim alii dolore malorum at, veniam perfecto salutandi cu nec, vix ad nonumes consulatu scripserit. At sit nonumy dolores aliquando, eu nam sumo legere. Eu maiorum adipisci torquatos his, vidit appareat eos no.\n" +
                "\n" +
                "Solet laboramus no quo, cu aperiam inermis vix. Eum animal graecis id, ne quodsi abhorreant sit. Tale persequeris te qui. Labitur invenire explicari in vix."
                + "Lorem ipsum dolor sit amet, qui mundi vivendum cu. Mazim dicant possit te his. Quo solet dicant prodesset eu, pri deseruisse concludaturque ea, saepe maiorum sea et. Impetus discere sed at. Vim eu novum erant integre, te tale voluptatibus est. Facer labores te mel.\n" +
                "\n" +
                "Dictas denique qualisque mea id, cu mei verear fabellas. Mel no autem nusquam, viderer oblique te mei. At minimum corpora consulatu vim. Cibo nominavi vis no, in verterem vulputate eos, essent iriure cu vel. Ius ferri expetendis ad, omnes aeterno nominati id his, eum debitis lobortis comprehensam id.\n" +
                "\n" +
                "Illud dicit nostrud sit no. Eu quod nostro pro. Ut gubergren mnesarchum has, nostro detracto scriptorem et quo, no illud phaedrum recteque sea. Ad his summo probatus recusabo. Qui amet tale viris et, ei his quodsi torquatos adipiscing. Laudem malorum no eum, accusam mandamus sit ex, est ut tractatos dissentiet. Dictas feugiat usu et, an his cibo appareat placerat, eu quis dignissim qui.\n" +
                "\n" +
                "Euripidis neglegentur eu per, denique singulis vel cu, malis dolore ne duo. Cum no iracundia persecuti expetendis. Vim alii dolore malorum at, veniam perfecto salutandi cu nec, vix ad nonumes consulatu scripserit. At sit nonumy dolores aliquando, eu nam sumo legere. Eu maiorum adipisci torquatos his, vidit appareat eos no.\n" +
                "\n" +
                "Solet laboramus no quo, cu aperiam inermis vix. Eum animal graecis id, ne quodsi abhorreant sit. Tale persequeris te qui. Labitur invenire explicari in vix.";
        if(mItems == null) {
            mItems = new ArrayList<Movie>();

            Movie movie1 = new Movie();
            movie1.setId(1);
            movie1.setTitle("Title1");
            movie1.setStudio("studio1");
            movie1.setDescription(description);
            movie1.setCardImageUrl("http://heimkehrend.raindrop.jp/kl-hacker/wp-content/uploads/2014/08/DSC02580.jpg");
            //movie1.setVideoUrl("http://corochann.com/wp-content/uploads/2015/07/MVI_0949.mp4");
            /* Google sample app's movie */
            movie1.setVideoUrl("http://mt.lenovo.com.cn/c/292/1495509642969.mp4");
            mItems.add(movie1);

            Movie movie2 = new Movie();
            movie2.setId(2);
            movie2.setTitle("Title2");
            movie2.setStudio("studio2");
            movie2.setDescription(description);
            movie2.setCardImageUrl("http://heimkehrend.raindrop.jp/kl-hacker/wp-content/uploads/2014/08/DSC02630.jpg");
            movie2.setVideoUrl("http://corochann.com/wp-content/uploads/2015/07/MVI_0962.mp4");
            /* Google sample app's movie */
//            movie2.setVideoUrl("http://2449.vod.myqcloud.com/2449_43b6f696980311e59ed467f22794e792.f20.mp4");
            mItems.add(movie2);

            Movie movie3 = new Movie();
            movie3.setId(3);
            movie3.setTitle("Title3");
            movie3.setStudio("studio3");
            movie3.setDescription(description);
            movie3.setCardImageUrl("http://heimkehrend.raindrop.jp/kl-hacker/wp-content/uploads/2014/08/DSC02529.jpg");
            movie3.setVideoUrl("http://mt.lenovo.com.cn/c/292/1495509642969.mp4");
            mItems.add(movie3);
        }
        return mItems;
    }

}