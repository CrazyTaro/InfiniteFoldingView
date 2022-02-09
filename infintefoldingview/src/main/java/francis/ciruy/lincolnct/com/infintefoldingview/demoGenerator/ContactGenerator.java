package francis.ciruy.lincolnct.com.infintefoldingview.demoGenerator;

import francis.ciruy.lincolnct.com.infintefoldingview.entity.BaseContactEntity;
import francis.ciruy.lincolnct.com.infintefoldingview.entity.impl.DemoEntity;

public final class ContactGenerator {
    public static BaseContactEntity createDemoOriginEntity() {
        DemoEntity demoEntity = new DemoEntity("总公司", "总公司", false);
        setChildEntity(demoEntity, "第一层分公司");
        for (BaseContactEntity demoEntity1 : demoEntity.subDepartment) {
            DemoEntity demoEntity2 = (DemoEntity) demoEntity1;
            setChildEntity(demoEntity2, "第二层分公司");
            for (BaseContactEntity demoEntity3 : demoEntity2.subDepartment) {
                DemoEntity demoEntity4 = (DemoEntity) demoEntity3;
                setChildEntity(demoEntity4, "第三层分公司");
            }
        }
        return demoEntity;
    }

    private static void setChildEntity(DemoEntity cur, String childName) {
        for (int i = 0; i < 10; i++) {
            cur.subDepartment.add(new DemoEntity(childName + i, cur.name));
        }
    }
}
