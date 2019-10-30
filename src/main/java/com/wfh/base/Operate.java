package com.wfh.base;

public enum Operate {

    eq("eq", "等于"),
    ne("ne", "不等于"),
    in("in", "包含"),
    ni("ni", "不包含"),
    bw("bw", "右匹配"),
    bn("bn", "右不匹配"),
    ew("ew", "左匹配"),
    en("en", "左不匹配"),
    cn("cn", "左右匹配"),
    nc("nc", "不左右匹配"),
    nu("nu", "空"),
    nn("nn", "非空"),
    lt("lt", "小于"),
    le("le", "小于等于"),
    gt("gt", "大于"),
    ge("ge", "大于等于"),
    ex("ex", "存在"),
    nx("nx", "不存在");

    private String id;
    private String name;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private Operate(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Operate of(String id) {
        if (id.equals(eq.getId())) {
            return eq;
        } else if (id.equals(ne.getId())) {
            return ne;
        } else if (id.equals(in.getId())) {
            return in;
        } else if (id.equals(ni.getId())) {
            return ni;
        } else if (id.equals(bw.getId())) {
            return bw;
        } else if (id.equals(bn.getId())) {
            return bn;
        } else if (id.equals(ew.getId())) {
            return ew;
        } else if (id.equals(en.getId())) {
            return en;
        } else if (id.equals(cn.getId())) {
            return cn;
        } else if (id.equals(nc.getId())) {
            return nc;
        } else if (id.equals(nu.getId())) {
            return nu;
        } else if (id.equals(nn.getId())) {
            return nn;
        } else if (id.equals(ex.getId())) {
            return ex;
        } else if (id.equals(nx.getId())) {
            return nx;
        } else if (id.equals(lt.getId())) {
            return lt;
        } else if (id.equals(le.getId())) {
            return le;
        } else if (id.equals(gt.getId())) {
            return gt;
        } else {
            return id.equals(ge.getId()) ? ge : null;
        }
    }

}
