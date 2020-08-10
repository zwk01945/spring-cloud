package bean;

/**************************************************************
 ***       S  T  A  G  E    多模块依赖项目                    ***
 **************************************************************
 *                                                            *
 *         Project Name : base             *
 *                                                            *
 *         File Name : PolicyType.java                           *
 *                                                            *
 *         Programmer : Mr.zhang                              *
 *                                                            *
 *         Start Date : 2020/8/3 15:19                       *
 *                                                            *
 *         Last Update : 2020/8/3 15:19                      *
 *                                                            *
 *------------------------------------------------------------*
 * Functions:                                                 *
 *   安全级别枚举                                               *
 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  */
public enum PolicyType {

    READ_ONLY("Read Only",1),
    WRITE_ONLY("Write Only",2),
    READ_AND_WRITE("Read and Write ",3);

    private String name;
    private Integer id;

    PolicyType(String name, Integer id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
