package Algorithm.filters;

/**
 * Created by MSI on 2016-04-23.
 */
public class FilterList {

    public FilterList() {

    }

    public static Integer[][] lowPass1() {
        return new Integer[][] {
                {1, 1, 1},
                {1, 1, 1},
                {1, 1, 1}
        };
    }

    public static Integer[][] lowPass2() {
        return new Integer[][] {
                {1, 1, 1},
                {1, 2, 1},
                {1, 1, 1}
        };
    }

    public static Integer[][] lowPass3() {
        return new Integer[][] {
                {1, 1, 1},
                {1, 4, 1},
                {1, 1, 1}
        };
    }

    public static Integer[][] lowPass4() {
        return new Integer[][] {
                {1, 2, 1},
                {2, 4, 2},
                {1, 2, 1}
        };
    }

    public static Integer[][] highPass1() {
        return new Integer[][] {
                {-1, -1, -1},
                {-1,  9, -1},
                {-1, -1, -1}
        };
    }

    public static Integer[][] highPass2() {
        return new Integer[][] {
                {0,  -1,  0},
                {-1,  5, -1},
                {0,  -1,  0}
        };
    }

    public static Integer[][] highPass3() {
        return new Integer[][] {
                {1,  -2,  1},
                {-2,  5, -2},
                {1,  -2,  1}
        };
    }

    public static Integer[][] highPass4() {
        return new Integer[][] {
                {0,   -1,  0},
                {-1,  20, -1},
                {0,   -1,  0}
        };
    }

    public static Integer[][] gradientDirectionalEast() {
        return new Integer[][] {
                {-1, 1,  1},
                {-1, -2, 1},
                {-1, 1,  1}
        };
    }

    public static Integer[][] gradientDirectionalSoutheast() {
        return new Integer[][] {
                {-1, -1, 1},
                {-1, -2, 1},
                { 1,  1, 1}
        };
    }

    public static Integer[][] gradientDirectionalSouth() {
        return new Integer[][] {
                {-1, -1, -1},
                { 1, -2,  1},
                { 1,  1,  1}
        };
    }

    public static Integer[][] gradientDirectionalSouthwest() {
        return new Integer[][] {
                { 1, -1, -1},
                { 1, -2, -1},
                { 1,  1,  1}
        };
    }

    public static Integer[][] gradientDirectionalWest() {
        return new Integer[][] {
                { 1,  1, -1},
                { 1, -2, -1},
                { 1,  1, -1}
        };
    }

    public static Integer[][] gradientDirectionalNorthwest() {
        return new Integer[][] {
                { 1,  1,  1},
                { 1, -2, -1},
                { 1, -1, -1}
        };
    }

    public static Integer[][] gradientDirectionalNorth() {
        return new Integer[][] {
                { 1,  1,  1},
                { 1, -2,  1},
                {-1, -1, -1}
        };
    }

    public static Integer[][] gradientDirectionalNortheast() {
        return new Integer[][] {
                { 1,  1,  1},
                {-1, -2,  1},
                {-1, -1,  1}
        };
    }
}
