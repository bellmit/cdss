package com.jhmk.cloudpage.controller;

import com.alibaba.fastjson.JSONObject;
import com.jhmk.cloudentity.base.BaseController;
import com.jhmk.cloudentity.earlywaring.entity.rule.Rule;
import com.jhmk.cloudservice.cdssPageService.DiagnoseService;
import com.jhmk.cloudpage.util.ParseRequestParamToRuleUtil;
import com.jhmk.cloudservice.warnService.service.RuleService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author ziyu.zhou
 * @date 2019/1/14 17:08
 * exe 诊断列表及功能
 */

@Controller
@RequestMapping("diagnose")
@Api(description = "诊断页面功能列表", value = "诊断页面控制层")
public class DiagnoseController extends BaseController {

    Logger logger = LoggerFactory.getLogger(DiagnoseController.class);
    @Autowired
    RuleService ruleService;
    @Autowired
    DiagnoseService diagnoseService;
    /**
     * 推荐诊断
     *
     * @param response
     * @param map
     */
    @ApiOperation(value = "记录病历推荐诊断", notes = "请求demo:{\"rycz\":\"脊髓型颈椎病\",\"cyzd\":\"脊髓型颈椎病\",\"shouyezhenduan\":[],\"mainIllName\":\"脊髓型颈椎病\",\"quezhen\":\"脊髓型颈椎病\",\"id\":\"BJDXDSYY#000551819000#1\",\"binganshouye\":{\"pat_info_sex_name\":\"女\",\"pat_info_age_value\":\"50\",\"pat_info_marital_status_name\":\"已婚\",\"pat_visit_dept_discharge_from_name\":\"骨科\",\"pat_visit_dept_admission_to_name\":\"1020500\"},\"binglizhenduan\":[{\"diagnosis_name\":\"前列腺结核\",\"diagnosis_num\":\"1\",\"diagnosis_sub_num\":\"0\",\"diagnosis_type_name\":\"初步诊断\"}],\"patient_id\":\"000551819000\",\"visit_id\":\"1\",\"ruyuanjilu\":[{\"key\":\"主诉\",\"value\":\"颈部疼痛伴双侧肢体麻木、无力20年,加重5年。\"},{\"key\":\"现病史\",\"value\":\"20年前，颈部疼痛，僵硬，伴有双上肢无力，行保守治疗，效果不理想。5年前头晕、颈部疼痛症状加重，上肢无力症状加重,出现手术麻木症状。就诊于当地医院,诊断为颈椎病，行牵引、按摩、药物等保守治疗，效果不明显。1年前出现走路不稳，伴有踩棉花感。无胸闷憋气、腹痛腹胀、恶心呕吐。鉴别诊断症状:无发热、寒战、大小便失禁。诊疗经过:为进一步治疗到我院就诊，经检查以\\\"脊髓型颈椎病\\\"收住院。一般情况:患者自受伤来神智清，精神状态一般，食欲一般，睡眠良好，小便正常，大便正常，体重无明显变化。\"},{\"key\":\"既往史\",\"value\":\"否认肝炎、结核、疟疾病史，否认高血压、心脏病史，否认糖尿病、脑血管疾病、精神疾病史，否认手术、外伤、输血史，否认食物、药物过敏史，预防接种史不详。\"},{\"key\":\"个人史\",\"value\":\"生于江苏省，久居本地，无疫区、疫情、疫水接触史，无牧区、矿山、高氟区、低碘区居住史无化学性物质、放射性物质、有毒物质接触史，无吸毒史，无吸烟、饮酒史\"},{\"key\":\"婚姻史\",\"value\":\"月经史:月经情况:初潮14岁，4/28天，末次月经6月20日。月经相关症状:月经周期规则,月经量中等，颜色正常。无血块、无痛经月经史]；子女2人;适龄结婚。\"},{\"key\":\"家族史\",\"value\":\"否认家族性遗传病史。\"},{\"key\":\"体格检查\",\"value\":\"体温37.0℃    脉搏60次／分    呼吸20次／分    血压120／80mmHg    发育正常，营养良好，正常面容，表情自如，自主体位，神志清楚，查体合作。全身皮肤粘膜无黄染，无皮疹、皮下出血、皮下结节、瘢痕，毛发分布正常，皮下无水肿，无肝掌、蜘蛛痣。全身浅表淋巴结无肿大。头颅无畸形、压痛、包块、无眼睑水肿，结膜正常，眼球正常，巩膜无黄染，瞳孔等大同圆，对光反射正常，外耳道无异常分泌物，乳突无压痛，无听力粗试障碍。嗅觉正常。口唇无发绀，口腔粘膜正常。舌苔正常，伸舌无偏斜、震颤，齿龈正常，咽部粘膜正常，扁桃体无肿大。颈软无抵抗，颈动脉搏动正常，颈静脉正常，气管居中，肝颈静脉回流征阴性，甲状腺正常，无压痛、震颤、血管杂音。胸廓正常，胸骨无叩痛，乳房正常对称。呼吸运动正常，肋间隙正常，语颤正常。部位标志线叩诊清音，呼吸规整，部位双肺呼吸音清晰，无胸膜摩擦音。心前区无隆起，心尖搏动正常，心浊音界正常，心率80次/分，律齐，各瓣膜听诊区未闻及杂音，无心包摩擦音。腹平坦，无腹壁静脉曲张，腹部柔软，无压痛、反跳痛，腹部无包块。肝脏未触及，脾脏未触及，Murphy氏征阴性，肾脏无叩击痛，无移动性浊音。肠鸣音正常，4次/分。肛门及外生殖器未查。脊柱四肢脊柱正常生理弯曲，四肢活动自如，无畸形、下肢静脉曲张、杵状指（趾），关节正常，下肢无浮肿。 四肢肌力、肌张力未见异常，双侧肱三头肌腱反射正常，双侧膝、跟腱反射减弱，双侧Babinski征阴性。\"},{\"key\":\"辅助检查\",\"value\":\"2015-04-09 超声心动图左房增大，室间隔增厚，升主动脉增宽，主动脉瓣反流（轻度），二尖瓣反流（轻度），左室舒张功能减退，LVEF 68%。我院    2015-04-09 BNP165pg/ml。我院\"}],\"doctor_id\":\"test\",\"doctor_name\":\"test_name\",\"warnSource\":\"门诊\",\"jianyanbaogao\":[{\"lab_item_name\":\"\",\"labTestItems\":[{\"name\":\"血钾\",\"lab_result_value\":\"H\",\"unit\":\"H\",\"result_status_code\":\"H\",\"referrence_range\":\"H\"}]}],\"jianchabaogao\":[],\"yizhu\":[],\"diseaseName\":\"前列腺结核\",\"diseaseNames\":[\"前列腺结核\"]}",
            httpMethod = "POST", responseContainer = "Map")
    @ApiResponses({@ApiResponse(code = 200, message = "成功")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "map", value = "请求参数", required = true, paramType = "body")
    })
    @PostMapping("/emrdiseaseinfo")
    public void emrdiseaseinfo(HttpServletResponse response, @RequestBody String map) {
        Map parse = (Map) JSONObject.parse(map);
        String str = ruleService.anaRule(parse);
        Rule rule = ParseRequestParamToRuleUtil.parseRequestParam(str);
        String s = diagnoseService.emrdiseaseinfo(rule,parse);
        //将接口返回的数据返回给前台
        wirte(response, s);
    }


}
