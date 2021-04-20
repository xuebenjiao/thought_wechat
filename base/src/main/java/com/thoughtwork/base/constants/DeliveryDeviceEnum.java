package com.thoughtwork.base.constants;

/**
 * 行者的枚举
 *
 * @author ShenHua
 *         2016年7月26日上午10:53:37
 */
public class DeliveryDeviceEnum {
    /**
     * 定制机型
     */
    public enum  PhoneModel{
        IWRIST (0,"IWRIST i9"),//东方拓宇
        CRUISE (1,"CRUISE"),//东大
        NEOLIX (2,"Neolix1YTO"),//新石器
        I6310C (3,"I6310B"),//优博讯
        P31(4,"P31"),
        NLSNFT1(5,"NLS-NFT10");

        private int code;
        private String phoneModel;

        PhoneModel(int code, String phoneModel) {
            this.code = code;
            this.phoneModel = phoneModel;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getPhoneModel() {
            return phoneModel;
        }

        public void setPhoneModel(String phoneModel) {
            this.phoneModel = phoneModel;
        }
    }
}
