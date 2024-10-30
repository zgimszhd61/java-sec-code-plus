package com.freedom.securitysamples.sootTest;

import soot.*;
import soot.options.Options;
import soot.toolkits.graph.UnitGraph;
import soot.toolkits.graph.ExceptionalUnitGraph;
import soot.util.cfgcmd.CFGToDotGraph;
import soot.util.dot.DotGraph;

import java.util.Collections;
import java.nio.file.Paths;

public class sootScan {
    public static void main(String[] args) {
        // 设置Soot的选项
        Options.v().set_prepend_classpath(true);
        Options.v().set_process_dir(Collections.singletonList(Paths.get("target", "classes", "com", "freedom", "securitysamples").toString()));
        Options.v().set_output_format(Options.output_format_none);
        Options.v().set_whole_program(true);
        Options.v().set_allow_phantom_refs(true);

        // 加载和设置主类
        SootClass mainClass = Scene.v().loadClassAndSupport("JavaCodeSimpleApplication");
        Scene.v().setMainClass(mainClass);
        Scene.v().loadNecessaryClasses();

        // 获取主方法
        SootMethod method = mainClass.getMethodByName("main");

        // 生成控制流图
        Body body = method.retrieveActiveBody();
        UnitGraph cfg = new ExceptionalUnitGraph(body);

        // 将控制流图转换为Dot格式并输出
        CFGToDotGraph cfgToDot = new CFGToDotGraph();
        DotGraph dotGraph = cfgToDot.drawCFG(cfg, body);
        dotGraph.plot("cfg.dot");

        System.out.println("Control Flow Graph has been generated and saved as cfg.dot");
    }
}
