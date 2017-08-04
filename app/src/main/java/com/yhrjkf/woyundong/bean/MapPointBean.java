package com.yhrjkf.woyundong.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/3/10.
 */

public class MapPointBean {


//      Recode : 200
//      Msg : 成功
//      Data : [{"latitude":"38.91368055555556","longitude":"115.48113444010417"},{"latitude":"38.913677300347224","longitude":"115.48115912543403"},{"latitude":"38.91367947048611","longitude":"115.48126437717013"},{"latitude":"38.91364013671875","longitude":"115.48132921006945"},{"latitude":"38.91361273871528","longitude":"115.48124348958333"},{"latitude":"38.913587239583336","longitude":"115.48115288628472"},{"latitude":"38.91358154296875","longitude":"115.48090711805555"},{"latitude":"38.91357231987847","longitude":"115.48068874782986"},{"latitude":"38.91356553819445","longitude":"115.48052517361111"},{"latitude":"38.913555501302085","longitude":"115.47963460286458"},{"latitude":"38.91355088975695","longitude":"115.47907497829861"},{"latitude":"38.913562825520835","longitude":"115.47883409288194"},{"latitude":"38.91383572048611","longitude":"115.47441704644098"},{"latitude":"38.91386311848958","longitude":"115.47382052951389"},{"latitude":"38.91402560763889","longitude":"115.47110731336805"},{"latitude":"38.91401665581597","longitude":"115.47096354166666"},{"latitude":"38.91400933159722","longitude":"115.47089029947917"},{"latitude":"38.91401475694445","longitude":"115.47084364149306"},{"latitude":"38.91404134114583","longitude":"115.47080023871528"},{"latitude":"38.91407958984375","longitude":"115.47077690972222"},{"latitude":"38.914088270399304","longitude":"115.47076985677083"},{"latitude":"38.9140966796875","longitude":"115.47086371527777"},{"latitude":"38.91410861545139","longitude":"115.47090766059027"},{"latitude":"38.914134657118055","longitude":"115.47093560112847"},{"latitude":"38.91413926866319","longitude":"115.47091118706597"},{"latitude":"38.9141468641493","longitude":"115.47095594618055"},{"latitude":"38.914173177083335","longitude":"115.47094265407986"},{"latitude":"38.91421359592014","longitude":"115.47098036024306"},{"latitude":"38.914227159288195","longitude":"115.47096950954861"},{"latitude":"38.91424424913195","longitude":"115.47095675998264"},{"latitude":"38.91420925564236","longitude":"115.47090657552083"},{"latitude":"38.91418240017361","longitude":"115.4708921983507"},{"latitude":"38.91415608723958","longitude":"115.47084581163195"},{"latitude":"38.914150390625","longitude":"115.47086859809028"},{"latitude":"38.914173719618056","longitude":"115.47087809244792"},{"latitude":"38.91418375651042","longitude":"115.47091335720486"},{"latitude":"38.91419243706597","longitude":"115.47093858506945"},{"latitude":"38.91420220269097","longitude":"115.4709507921007"},{"latitude":"38.914211697048614","longitude":"115.47095893012153"},{"latitude":"38.91419487847222","longitude":"115.47096571180556"},{"latitude":"38.91416042751736","longitude":"115.47095648871527"},{"latitude":"38.914157443576386","longitude":"115.47096028645834"},{"latitude":"38.91416829427083","longitude":"115.47095648871527"},{"latitude":"38.91419406467014","longitude":"115.47094807942709"},{"latitude":"38.91422770182292","longitude":"115.4709388563368"},{"latitude":"38.914212510850696","longitude":"115.47094699435763"},{"latitude":"38.91419976128472","longitude":"115.47095269097223"},{"latitude":"38.91418294270834","longitude":"115.47094509548612"},{"latitude":"38.914171820746525","longitude":"115.47093858506945"},{"latitude":"38.9141468641493","longitude":"115.47092963324653"},{"latitude":"38.91413791232639","longitude":"115.47091986762153"},{"latitude":"38.914134928385415","longitude":"115.47090359157986"},{"latitude":"38.914157443576386","longitude":"115.47091688368056"},{"latitude":"38.914164496527775","longitude":"115.47090223524306"},{"latitude":"38.91417724609375","longitude":"115.4708970811632"},{"latitude":"38.9141685655382","longitude":"115.47092041015625"},{"latitude":"38.91413953993055","longitude":"115.47095458984376"},{"latitude":"38.914165852864585","longitude":"115.47093397352431"},{"latitude":"38.91417941623264","longitude":"115.47091471354166"},{"latitude":"38.91417724609375","longitude":"115.47089328342014"},{"latitude":"38.914196234809026","longitude":"115.47087700737848"},{"latitude":"38.91412299262153","longitude":"115.47087782118055"},{"latitude":"38.91408501519097","longitude":"115.47085232204861"},{"latitude":"38.91407606336806","longitude":"115.47087375217014"},{"latitude":"38.91417453342014","longitude":"115.47087266710069"},{"latitude":"38.91422770182292","longitude":"115.47089328342014"}]


    private String Recode;
    private String Msg;
    private List<DataBean> Data;

    public String getRecode() {
        return Recode;
    }

    public void setRecode(String Recode) {
        this.Recode = Recode;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String Msg) {
        this.Msg = Msg;
    }

    public List<DataBean> getData() {
        return Data;
    }

    public void setData(List<DataBean> Data) {
        this.Data = Data;
    }

    public static class DataBean {
        /**
         * latitude : 38.91368055555556
         * longitude : 115.48113444010417
         */

        private String latitude;
        private String longitude;

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }
    }
}
