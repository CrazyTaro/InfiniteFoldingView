package francis.ciruy.lincolnct.com.infintefoldingview.entity;


import android.support.annotation.Nullable;

public abstract class BaseEntity {
    protected String alias;
    protected String name;
    protected String id;

    @Override
    public boolean equals(@Nullable Object obj) {
        return super.equals(obj);
    }

//    @Override
//    public int hashCode() {
//        if (id != null) {
//            return id;
//        }
//        return super.hashCode();
//    }
}
