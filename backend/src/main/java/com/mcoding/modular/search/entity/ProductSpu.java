package com.mcoding.modular.search.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * <p>
 * 商品SPU
 * </p>
 *
 * @author wzt
 * @since 2022-06-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("product_spu")
@ApiModel(value="ProductSpu", description="商品SPU")
public class ProductSpu implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "编号")
    @TableField("code")
    private String code;

    @ApiModelProperty(value = "名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "副标题")
    @TableField("sub_title")
    private String subTitle;

    @ApiModelProperty(value = "描述")
    @TableField("description")
    private String description;

    @ApiModelProperty(value = "是否是多规")
    @TableField("spec_type")
    private Boolean specType;

    @ApiModelProperty(value = "备注（后台用，前端暂不展示）")
    @TableField("memo")
    private String memo;

    @ApiModelProperty(value = "pc详情")
    @TableField("introduce_pc")
    private String introducePc;

    @ApiModelProperty(value = "移动详情")
    @TableField("introduce_mobile")
    private String introduceMobile;

    @ApiModelProperty(value = "小程序详情")
    @TableField("introduce_program")
    private String introduceProgram;

    @ApiModelProperty(value = "生产厂家")
    @TableField("manufacturer_name")
    private String manufacturerName;

    @ApiModelProperty(value = "名称拼音首字母")
    @TableField("pinyin")
    private String pinyin;

    @ApiModelProperty(value = "条形码")
    @TableField("bar_code")
    private String barCode;

    @ApiModelProperty(value = "规格")
    @TableField("spec")
    private String spec;

    @ApiModelProperty(value = "搜索关键字")
    @TableField("keywords")
    private String keywords;

    @ApiModelProperty(value = "销售单位")
    @TableField("unit")
    private String unit;

    @ApiModelProperty(value = "重量（预留，算运费用）")
    @TableField("weight")
    private Double weight;

    @ApiModelProperty(value = "重量单位")
    @TableField("weight_unit")
    private String weightUnit;

    @ApiModelProperty(value = "体积（预留，算运费用）")
    @TableField("volumn")
    private String volumn;

    @ApiModelProperty(value = "是否纳入预算管控")
    @TableField("check_budget")
    private Boolean checkBudget;

    @ApiModelProperty(value = "创建人")
    @TableField("created_by")
    private String createdBy;

    @ApiModelProperty(value = "创建时间")
    @TableField("created_date")
    private Date createdDate;

    @ApiModelProperty(value = "修改人")
    @TableField("last_modified_by")
    private String lastModifiedBy;

    @ApiModelProperty(value = "修改时间")
    @TableField("last_modified_date")
    private Date lastModifiedDate;

    @ApiModelProperty(value = "是否删除")
    @TableField("is_deleted")
    private Boolean isDeleted;

    @TableField("catalog_id")
    private Long catalogId;

    @TableField("company_id")
    private Long companyId;

    @TableField("category_id")
    private Long categoryId;

    @TableField("brand_id")
    private Long brandId;

    @TableField("from_category_id")
    private Long fromCategoryId;

    @TableField("supply_company_id")
    private Long supplyCompanyId;

    @TableField("purchase_class_id")
    private Long purchaseClassId;

    @ApiModelProperty(value = "预算分类ID")
    @TableField("budget_class_id")
    private Integer budgetClassId;

    @ApiModelProperty(value = "平台商品ID")
    @TableField("base_product_id")
    private Long baseProductId;

    @ApiModelProperty(value = "平台商品code")
    @TableField("base_product_code")
    private String baseProductCode;

    @ApiModelProperty(value = "预留字段A")
    @TableField("field_a")
    private String fieldA;

    @ApiModelProperty(value = "预留字段B")
    @TableField("field_b")
    private String fieldB;

    @ApiModelProperty(value = "预留字段C")
    @TableField("field_c")
    private String fieldC;

    @ApiModelProperty(value = "预留字段D")
    @TableField("field_d")
    private String fieldD;

    @ApiModelProperty(value = "预留字段E")
    @TableField("field_e")
    private String fieldE;

    @ApiModelProperty(value = "ean码")
    @TableField("ean_code")
    private String eanCode;

    @ApiModelProperty(value = "hsCode")
    @TableField("hs_code")
    private String hsCode;

    @ApiModelProperty(value = "upc码")
    @TableField("upc")
    private String upc;

    @TableField("platform_category_code")
    private String platformCategoryCode;

    @TableField("platform_category_id")
    private Long platformCategoryId;

    @ApiModelProperty(value = "运费模板id")
    @TableField("freight_template_id")
    private Long freightTemplateId;


}
