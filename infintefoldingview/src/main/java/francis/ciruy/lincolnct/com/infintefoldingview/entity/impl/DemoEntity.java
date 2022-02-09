package francis.ciruy.lincolnct.com.infintefoldingview.entity.impl;


import francis.ciruy.lincolnct.com.infintefoldingview.entity.BaseContactEntity;

public class DemoEntity extends BaseContactEntity {
    public String name;
    public String info;

    public DemoEntity(String name, String info) {
        this.name = name;
        this.info = info;
    }

    public DemoEntity(String name, String info, Boolean special1) {
        this(name, info);
        this.isLast = special1;
    }

    public void setSpecial2(Boolean special2) {
        this.isSelected = special2;
    }
}
