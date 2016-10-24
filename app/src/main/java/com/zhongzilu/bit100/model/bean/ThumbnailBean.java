package com.zhongzilu.bit100.model.bean;

/**
 * 缩略图实体类
 * Created by zhongzilu on 2016-10-21.
 */
public class ThumbnailBean {
//    "full": {
//        "url": "http://www.bit100.com/wp-content/uploads/2016/09/9fa0216d2b9bf80733032f076d4aec0a-1.jpg",
//        "width": 553,
//        "height": 345
//    },
//    "thumbnail": {
//        "url": "http://www.bit100.com/wp-content/uploads/2016/09/9fa0216d2b9bf80733032f076d4aec0a-1-150x94.jpg",
//        "width": 150,
//        "height": 94
//    },
//    "medium": {
//        "url": "http://www.bit100.com/wp-content/uploads/2016/09/9fa0216d2b9bf80733032f076d4aec0a-1-300x187.jpg",
//        "width": 300,
//        "height": 187
//    },
//    "medium_large": {
//        "url": "http://www.bit100.com/wp-content/uploads/2016/09/9fa0216d2b9bf80733032f076d4aec0a-1.jpg",
//        "width": 553,
//        "height": 345
//    },
//    "large": {
//        "url": "http://www.bit100.com/wp-content/uploads/2016/09/9fa0216d2b9bf80733032f076d4aec0a-1.jpg",
//        "width": 553,
//        "height": 345
//    }

    public DataObject full,
            thumbnail,
            medium,
            medium_large,
            large;

    public class DataObject{
        public String url;
        public int width,
                height;
    }
}
