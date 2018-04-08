package netease;

public class Api {
    private final static String BaseURL = "http://music.163.com/";

    /**
     * 获取用户歌单
     *
     * @param uid
     * @return
     */
    public static UrlParamPair getPlaylistOfUser(String uid) {
        UrlParamPair upp = new UrlParamPair();
        upp.setUrl(BaseURL + "weapi/user/playlist?csrf_token=");
        upp.addPara("offset", 0);
        upp.addPara("uid", uid);
        upp.addPara("limit", 20);
        upp.addPara("csrf_token", "nothing");
        return upp;
    }

    /**
     * 获取歌单详情
     *
     * @param playlist_id
     * @return
     */
    public static UrlParamPair getDetailOfPlaylist(String playlist_id) {
        UrlParamPair upp = new UrlParamPair();
        //upp.setUrl(BaseURL + "weapi/v3/playlist/detail?csrf_token=");
        upp.addPara("id", playlist_id);
        upp.addPara("offset", 0);
        upp.addPara("total", "True");
        upp.addPara("limit", 100);
        upp.addPara("n", 1000);
        upp.addPara("csrf_token", "nothing");
        return upp;
    }

    /**
     * 获取歌单详情
     *
     * @param musicId
     * @return
     */
    public static UrlParamPair getCommentList(String musicId,int offset) {
        UrlParamPair upp = new UrlParamPair();
        //upp.setUrl(BaseURL + "weapi/v3/playlist/detail?csrf_token=");
        upp.addPara("id", musicId);
        upp.addPara("offset", offset);
        if(offset==0){
            upp.addPara("total", "True");
        }else {
            upp.addPara("total", "False");
        }
        upp.addPara("limit", 100);
        upp.addPara("n", 1000);
        upp.addPara("csrf_token", "nothing");
        return upp;
    }
    //todo:analyse more api
    /**
     * 搜索歌曲
     *
     * 在原地址上面传递的参数可以分析：
     type类型的不同，搜索的类型不同
     type=1             单曲

     type=10           专辑

     type=100         歌手

     type=1000      歌单

     type=1002      用户

     type=1004       MV

     type=1006      歌词

     type=1009      主播电台
     * @param s;
     * @return
     */
    public static UrlParamPair SearchMusicList(String s,String type) {
        UrlParamPair upp = new UrlParamPair();
        upp.addPara("s", s);
        upp.addPara("type",type);
        upp.addPara("offset", 0);
        upp.addPara("total", "True");
        upp.addPara("limit", 20);
        upp.addPara("n", 1000);
        upp.addPara("csrf_token", "nothing");
        return upp;
    }



}
