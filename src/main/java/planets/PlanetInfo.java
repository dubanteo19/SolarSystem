package planets;


import java.util.HashMap;

public class PlanetInfo {
    public static HashMap<String, String> infoMap = new HashMap<>();

    public static void initData() {
        String mercuryInfo = """
                Sao Thuy (Mercury)
                Ban kinh: 2,439.7 km
                Toc do quy dao: 47.87 km/s
                Toc do quay: 10.83 km/h
                Khoi luong: 3.301 × 10²³ kg
                Dieu thu vi: Sao Thuy khong co bau khi quyyen, khien no tro thanh 
                hanh tinh nong nhat vao ban ngay va lanh nhat vao ban dem.
                """;
        infoMap.put("mercury", mercuryInfo);

        String venusInfo = """
                Sao Kim (Venus)
                Ban kinh: 6,051.8 km
                Toc do quy dao: 35.02 km/s
                Toc do quay: 6.52 km/h (quay nguoc chieu)
                Khoi luong: 4.867 × 10²⁴ kg
                Dieu thu vi: Sao Kim co thoi gian quay dai nhat
                 trong he Mat Troi, mat 243 ngay Trai Dat de quay mot vong.
                """;
        infoMap.put("venus", venusInfo);

        String earthInfo = """
                Trai Dat (Earth)
                Ban kinh: 6,371 km
                Toc do quy dao: 29.78 km/s
                Toc do quay: 1,670 km/h
                Khoi luong: 5.972 × 10²⁴ kg
                Dieu thu vi: Trai Dat la hanh tinh duy nhat duoc biet den la co su song.
                """;
        infoMap.put("earth", earthInfo);

        String marsInfo = """
                Sao Hoa (Mars)
                Ban kinh: 3,396.2 km
                Toc do quy dao: 24.077 km/s
                Toc do quay: 868.22 km/h
                Khoi luong: 6.417 × 10²³ kg
                Dieu thu vi: Sao Hoa co nui lua lon nhat trong 
                he Mat Troi, Olympus Mons.
                """;
        infoMap.put("mars", marsInfo);

        String jupiterInfo = """
                Sao Moc (Jupiter)
                Ban kinh: 69,911 km
                Toc do quy dao: 13.07 km/s
                Toc do quay: 43,000 km/h
                Khoi luong: 1.898 × 10²⁷ kg
                Dieu thu vi: Sao Moc co Vet Do Lon, mot con bao 
                khong lo da ton tai it nhat 400 nam.
                """;
        infoMap.put("jupiter", jupiterInfo);

        String saturnInfo = """
                Sao Tho (Saturn)
                Ban kinh: 58,232 km
                Toc do quy dao: 9.69 km/s
                Toc do quay: 9,640 km/h
                Khoi luong: 5.683 × 10²⁶ kg
                Dieu thu vi: Sao Tho noi tieng voi cac vanh dai duoc
                 tao thanh tu da va bang.
                """;
        infoMap.put("saturn", saturnInfo);
        String moonInfo = """
                Mat Trang (Moon)
                Ban kinh: 1,737.1 km
                Toc do quy dao: 1.022 km/s
                Toc do quay: 1,622 km/h
                Khoi luong: 7.35 × 10²² kg
                Dieu thu vi: Mat Trang co mot mat trang duy nhat, 
                luon quay ve phia Trai Dat.
                """;
        infoMap.put("moon", moonInfo);

        String phobosInfo = """
                Phobos (Phobos)
                Ban kinh: 11.1 km
                Toc do quy dao: 2.138 km/s
                Khoi luong: 1.065 × 10¹⁶ kg
                Dieu thu vi: Phobos di chuyen nhanh hon so voi Mat Trang,
                 no quay xung quanh Sao Hoa trong chi 7 gio.
                """;
        infoMap.put("phobos", phobosInfo);

        String deimosInfo = """
                Deimos (Deimos)
                Ban kinh: 6.2 km
                Toc do quy dao: 1.35 km/s
                Khoi luong: 1.476 × 10¹⁵ kg
                Dieu thu vi: Deimos la tinh the lon hon Phobos, nhung no xa Sao Hoa hon.
                """;
        infoMap.put("deimos", deimosInfo);

        String mirandaInfo = """
                Miranda (Miranda)
                Ban kinh: 471.6 km
                Toc do quy dao: 1.41 km/s
                Khoi luong: 6.2 × 10¹⁹ kg
                Dieu thu vi: Miranda co nhieu hoat dong dia chat khong lo,
                bao gom cac hem nui vuong va ranh leo.
                """;

        infoMap.put("miranda", mirandaInfo);
        String neptuneInfo = """
                Sao Hai (Neptune)
                Ban kinh: 24,622 km
                Toc do quy dao: 5.43 km/s
                Khoi luong: 1.024 × 10²⁶ kg
                Dieu thu vi: Sao Hai co gio thoi voi toc do cao nhat trong he Mat Troi, dat 2,100 km/h.
                """;
        infoMap.put("neptune", neptuneInfo);
        String arielInfo = """
                Ariel (Ariel)
                Ban kinh: 1,158.8 km
                Toc do quy dao: 1.43 km/s
                Khoi luong: 1.35 × 10²¹ kg
                Dieu thu vi: Ariel co nhieu su hoat dong dia chat, 
                bao gom cac vung nui lua .
                """;
        infoMap.put("ariel", arielInfo);

        String oberonInfo = """
                Oberon (Oberon)
                Ban kinh: 1,523.4 km
                Toc do quy dao: 1.52 km/s
                Khoi luong: 3.0 × 10²¹ kg
                Dieu thu vi: Oberon la mot trong nhung tinh the lon nhat cua Uranus.
                """;
        infoMap.put("oberon", oberonInfo);

        String titaniaInfo = """
                Titania (Titania)
                Ban kinh: 1,578.9 km
                Toc do quy dao: 1.38 km/s
                Khoi luong: 3.5 × 10²¹ kg
                Dieu thu vi: Titania la tinh the lon nhat trong so cac tinh the cua Uranus.
                """;
        infoMap.put("titania", titaniaInfo);

        String enceladusInfo = """
                Enceladus (Enceladus)
                Ban kinh: 504.2 km
                Toc do quy dao: 0.213 km/s
                Khoi luong: 1.08 × 10¹⁹ kg
                Dieu thu vi: Enceladus co cac suon nuoc nong phun ra, la mot trong
                 nhung tinh the co hieu ung geologic moi nhat.
                """;
        infoMap.put("enceladus", enceladusInfo);

        String mimasInfo = """
                Mimas (Mimas)
                Ban kinh: 396 km
                Toc do quy dao: 0.213 km/s
                Khoi luong: 3.75 × 10¹⁸ kg
                Dieu thu vi: Mimas co mot vat nuoc to lon, khi nhin tu xa,
                 no co hinh dang cua mot qua dau.
                """;
        infoMap.put("mimas", mimasInfo);

        String callistoInfo = """
                Callisto (Callisto)
                Ban kinh: 2,410.3 km
                Toc do quy dao: 8.2 km/s
                Khoi luong: 1.08 × 10²³ kg
                Dieu thu vi: Callisto la tinh the lon nhat cua Sao Moc.
                """;
        infoMap.put("callisto", callistoInfo);

        String ganymedeInfo = """
                Ganymede (Ganymede)
                Ban kinh: 2,631.2 km
                Toc do quy dao: 10.9 km/s
                Khoi luong: 1.48 × 10²³ kg
                Dieu thu vi: Ganymede la tinh the lon nhat trong he mat troi
                 va co mot bong nuoc bao phu.
                """;
        infoMap.put("ganymede", ganymedeInfo);

        String europaInfo = """
                Europa (Europa)
                Ban kinh: 1,560.8 km
                Toc do quy dao: 5.4 km/s
                Khoi luong: 4.8 × 10²² kg
                Dieu thu vi: Europa co mot la chieu, mat nuoc bao phu va 
                la mot trong nhung cua so tim kiem su song ngoai Trai Dat.
                """;
        infoMap.put("europa", europaInfo);

        String ioInfo = """
                Io (Io)
                Ban kinh: 1,821.6 km
                Toc do quy dao: 8.7 km/s
                Khoi luong: 8.93 × 10²² kg
                Dieu thu vi: Io la tinh the geologic nhat trong he mat troi, 
                voi nhieu nui lua hoat dong.
                """;
        infoMap.put("io", ioInfo);
    }


    public static String getInfo(String name) {
        return infoMap.get(name);
    }

    public static void main(String[] args) {
        System.out.println(infoMap.get("venus"));
    }
}
