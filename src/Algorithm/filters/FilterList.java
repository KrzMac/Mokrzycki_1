package Algorithm.filters;

/**
 * Created by MSI on 2016-04-23.
 */
public class FilterList {

    public FilterList() {

    }

    public Integer[][] lowPass1() {
        return new Integer[][] {
                {1, 1, 1},
                {1, 1, 1},
                {1, 1, 1}
        };
    }

    public Integer[][] lowPass2() {
        return new Integer[][] {
                {1, 1, 1},
                {1, 2, 1},
                {1, 1, 1}
        };
    }

    public Integer[][] lowPass3() {
        return new Integer[][] {
                {1, 1, 1},
                {1, 4, 1},
                {1, 1, 1}
        };
    }

    public Integer[][] lowPass4() {
        return new Integer[][] {
                {1, 2, 1},
                {2, 4, 2},
                {1, 2, 1}
        };
    }

    public Integer[][] highPass1() {
        return new Integer[][] {
                {-1, -1, -1},
                {-1,  9, -1},
                {-1, -1, -1}
        };
    }

    public Integer[][] gradientDirectionalEast() {
        return new Integer[][] {
                {-1, 1,  1},
                {-1, -2, 1},
                {-1, 1,  1}
        };
    }


}
