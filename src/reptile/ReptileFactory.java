package reptile;

/**
 * @author ReStartLin
 * @data 2018/12/5 12:04
 * @classDesc: 功能描述:
 */
public class ReptileFactory {
    public static BaseReptile getInstance(String type) {
        switch (type) {
            case "97973":
                return new Reptile97973();
        }
        return null;
    }

}
