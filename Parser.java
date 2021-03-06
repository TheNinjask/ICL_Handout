/* Parser.java */
/* Generated By:JavaCC: Do not edit this line. Parser.java */
/** MA imports */

import parserElements.*;
import parserExceptions.*;
import compilerElements.*;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
/** ID lister. */
public class Parser implements ParserConstants {

  private static final String OUT_FILE = "-o";

  private static final String IN_FILE = "-i";

  private static final String JAR_JASMINE = "-j";
  /** Main entry point. */
  public static void main(String args[]) {
    boolean compile = false;
    String filename = null;
    String inputFile = null;
    String jasminJar = null;
    for(int i=0; i<args.length; i++){
      switch(args[i]){
        case OUT_FILE:
          if(++i<args.length){
            filename = args[i];
            if(!filename.endsWith(".j"))
              filename = String.format("%s.j", filename);
            compile = true;
          }else{
            System.out.println(String.format("Flag %s need at least 1 argument!", args[i-1]));
            System.exit(1);
          }
          break;
        case IN_FILE:
          if(++i<args.length){
            inputFile = args[i];
          }else{
            System.out.println(String.format("Flag %s need at least 1 argument!", args[i-1]));
            System.exit(1);
          }
          break;
        case JAR_JASMINE:
          if(++i<args.length){
            jasminJar = args[i];
          }else{
            System.out.println(String.format("Flag %s need at least 1 argument!", args[i-1]));
            System.exit(1);
          }
          break;
        default:
          System.out.println(String.format("Flag %s not recognized!", args[i]));
          System.exit(1);
      }
    }
    Parser parser;
    if(inputFile==null)
      parser = new Parser(System.in);
    else
      try{
        parser = new Parser(new FileInputStream(inputFile));
      }catch(FileNotFoundException e){
        System.out.println(e.getMessage());
        System.exit(1);
        return;
      }
    if(compile)
      compiler(parser, filename, jasminJar);
    else
      interpreter(parser);
  }

  private static final void interpreter(Parser parser){
      ASTNode exp;
      Env env = new Env<IValue>();
      while (true) {
        try {
          System.out.print("> ");
          exp = parser.Start();
          exp.eval(env);//.show();
          System.out.println();
        } catch (Exception e) {
          System.out.println (String.format("Syntax Error: %s",e.getMessage()));
          parser.ReInit(System.in);
        }
      }
    }

    private static final void compiler(Parser parser, String filename, String jasminJar){
      ASTNode exp;
      Env env = new Env<FrameComp>();
      Env envType = new Env<IType>();
      CodeBlock comp = CodeBlock.getInstance();
      try {
        System.out.print("> ");
        exp = parser.Start();
        //exp.typecheker(envType);
        exp.compile(env, envType, comp);
        comp.dump(filename);
        if(jasminJar!=null)
          comp.compJasminWin(filename, jasminJar);
      } catch (Exception e) {
        System.out.println(String.format("Syntax Error: %s",e.getMessage()));
      }
    }

  static ASTNode cls() throws ParseException {//TODO rework
  for(int i = 0; i<50; i++){
    System.out.println();
  }
  return new ASTBool(true);
  }

  static final public ASTNode Start() throws ParseException {ASTNode t;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case PRINTLN:
    case FUN:
    case NEW:
    case IF:
    case WHILE:
    case NOT:
    case LCBRACKET:
    case QUOTE:
    case DEASSIGN:
    case LET:
    case Bool:
    case Id:
    case Num:
    case PLUS:
    case MINUS:
    case LPAR:{
      t = EM();
      jj_consume_token(FINAL);
      break;
      }
    case QUIT:{
      jj_consume_token(QUIT);
      jj_consume_token(LPAR);
      jj_consume_token(RPAR);
      jj_consume_token(FINAL);
t=null;System.exit(0);
      break;
      }
    case CLEAR:{
      jj_consume_token(CLEAR);
      jj_consume_token(FINAL);
t=cls();
      break;
      }
    default:
      jj_la1[0] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
{if ("" != null) return t;}
    throw new Error("Missing return statement in function");
  }

  static final public ASTNode EM() throws ParseException {ASTNode t,t2;
    t = E();
    label_1:
    while (true) {
      if (jj_2_1(2)) {
        ;
      } else {
        break label_1;
      }
      jj_consume_token(SEMICOLON);
      t2 = EM();
t=new ASTSeq(t,t2);
    }
    if (jj_2_2(2)) {
      jj_consume_token(SEMICOLON);
    } else {
      ;
    }
{if ("" != null) return t;}
    throw new Error("Missing return statement in function");
  }

  static final public ASTNode E() throws ParseException {Token op;
  ASTNode t1, t2;
    t1 = EA();
    if (jj_2_3(2)) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case GREATER_EQUAL:{
        op = jj_consume_token(GREATER_EQUAL);
        break;
        }
      case GREATER:{
        op = jj_consume_token(GREATER);
        break;
        }
      case LESS_EQUAL:{
        op = jj_consume_token(LESS_EQUAL);
        break;
        }
      case LESS:{
        op = jj_consume_token(LESS);
        break;
        }
      case MATCH:{
        op = jj_consume_token(MATCH);
        break;
        }
      case AND:{
        op = jj_consume_token(AND);
        break;
        }
      case OR:{
        op = jj_consume_token(OR);
        break;
        }
      default:
        jj_la1[1] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      t2 = EA();
if (op.kind == MATCH)
                        t1 = new ASTEq(t1,t2);
                   else if(op.kind == GREATER)
                        t1 = new ASTGreater(t1,t2);
                   else if(op.kind == GREATER_EQUAL)
                        t1 = new ASTGreaterEq(t1,t2);
                   else if(op.kind == LESS)
                        t1 = new ASTLess(t1,t2);
                   else if(op.kind == LESS_EQUAL)
                        t1 = new ASTLessEq(t1,t2);
                   else if (op.kind == AND)
                        t1 = new ASTAnd(t1,t2);
                   else
                        t1 = new ASTOr(t1,t2);
    } else {
      ;
    }
{if ("" != null) return t1;}
    throw new Error("Missing return statement in function");
  }

  static final public ASTNode EA() throws ParseException {Token op;
  ASTNode t1, t2;
    t1 = T();
    label_2:
    while (true) {
      if (jj_2_4(2)) {
        ;
      } else {
        break label_2;
      }
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case PLUS:{
        op = jj_consume_token(PLUS);
        break;
        }
      case MINUS:{
        op = jj_consume_token(MINUS);
        break;
        }
      default:
        jj_la1[2] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      t2 = EA();
if (op.kind == PLUS)
                        t1 = new ASTPlus(t1,t2);
                   else
                        t1 = new ASTSub(t1,t2);
    }
{if ("" != null) return t1;}
    throw new Error("Missing return statement in function");
  }

  static final public ASTNode T() throws ParseException {Token op;
  ASTNode t1, t2;
  List<ASTNode> pseudoT;
    t1 = F();
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case ASSIGN:{
      op = jj_consume_token(ASSIGN);
      t2 = E();
t1 = new ASTAssign(t1,t2);
      break;
      }
    case LPAR:{
      jj_consume_token(LPAR);
      pseudoT = AL();
      jj_consume_token(RPAR);
t1 = new ASTApply(pseudoT, t1);
      break;
      }
    case DOT:{
      jj_consume_token(DOT);
      op = jj_consume_token(Id);
t1 = new ASTGetField(t1, op.image);
      break;
      }
    default:
      jj_la1[4] = jj_gen;
      label_3:
      while (true) {
        if (jj_2_5(2)) {
          ;
        } else {
          break label_3;
        }
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case TIMES:{
          op = jj_consume_token(TIMES);
          break;
          }
        case DIV:{
          op = jj_consume_token(DIV);
          break;
          }
        default:
          jj_la1[3] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
        t2 = T();
if(op.kind == TIMES)
                        t1 = new ASTTimes(t1,t2);
                    else
                        t1 = new ASTDiv(t1, t2);
      }
    }
{if ("" != null) return t1;}
    throw new Error("Missing return statement in function");
  }

  static final public List<ASTNode> AL() throws ParseException {Token op;
  ASTNode t1, t2;
  List<ASTNode> params = new ArrayList();
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case PRINTLN:
    case FUN:
    case NEW:
    case IF:
    case WHILE:
    case NOT:
    case LCBRACKET:
    case QUOTE:
    case DEASSIGN:
    case LET:
    case Bool:
    case Id:
    case Num:
    case PLUS:
    case MINUS:
    case LPAR:{
      t1 = EM();
      label_4:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case COMMA:{
          ;
          break;
          }
        default:
          jj_la1[5] = jj_gen;
          break label_4;
        }
        op = jj_consume_token(COMMA);
        t2 = EM();
params.add(t2);
      }
params.add(t1);
      break;
      }
    default:
      jj_la1[6] = jj_gen;
      ;
    }
{if ("" != null) return params;}
    throw new Error("Missing return statement in function");
  }

  static final public Map<String, ASTNode> PL() throws ParseException {Token out, in;
  ASTNode t, t2;
  Map<String, ASTNode> type = new LinkedHashMap();
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case Id:{
      out = jj_consume_token(Id);
      jj_consume_token(COLON);
      t = TY();
      label_5:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case COMMA:{
          ;
          break;
          }
        default:
          jj_la1[7] = jj_gen;
          break label_5;
        }
        jj_consume_token(COMMA);
        in = jj_consume_token(Id);
        jj_consume_token(COLON);
        t2 = TY();
if(type.put(in.image, t2)!=null)
      {if (true) throw new TypeError(String.format("Id (%s) assigned twice at same level!", in.image));}
      }
if(type.put(out.image, t)!=null)
      {if (true) throw new TypeError(String.format("Id (%s) assigned twice at same level!", out.image));}
      break;
      }
    default:
      jj_la1[8] = jj_gen;
      ;
    }
{if ("" != null) return type;}
    throw new Error("Missing return statement in function");
  }

  static final public ASTNode TY() throws ParseException {Token n;
  ASTNode t, t2;
  List<ASTNode> types = new ArrayList();
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case INT:{
      n = jj_consume_token(INT);
t = new ASTIntType();
      break;
      }
    case BOOL:{
      n = jj_consume_token(BOOL);
t = new ASTBoolType();
      break;
      }
    case STRING:{
      n = jj_consume_token(STRING);
t = new ASTStringType();
      break;
      }
    case STRUCT:{
      n = jj_consume_token(STRUCT);
t = new ASTStructType();
      break;
      }
    case REF:{
      n = jj_consume_token(REF);
      t = TY();
t = new ASTRefType(t);
      break;
      }
    case LPAR:{
      jj_consume_token(LPAR);
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case STRUCT:
      case STRING:
      case INT:
      case BOOL:
      case REF:
      case LPAR:{
        t = TY();
        label_6:
        while (true) {
          switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
          case COMMA:{
            ;
            break;
            }
          default:
            jj_la1[9] = jj_gen;
            break label_6;
          }
          jj_consume_token(COMMA);
          t2 = TY();
types.add(t2);
        }
types.add(t);
        break;
        }
      default:
        jj_la1[10] = jj_gen;
        ;
      }
      jj_consume_token(RPAR);
      t = TY();
t = new ASTFunType(types, t);
      break;
      }
    default:
      jj_la1[11] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
{if ("" != null) return t;}
    throw new Error("Missing return statement in function");
  }

  static final public ASTNode F() throws ParseException {Token n, a;
  ASTNode t1, t2, t3;
  Map<String, ASTNode> map = new LinkedHashMap ();
  Map<String, ASTNode> mapType = new LinkedHashMap ();
  Set<String> params = new HashSet();
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case Num:{
      n = jj_consume_token(Num);
t1 = new ASTNum(Integer.parseInt(n.image));
      break;
      }
    case Bool:{
      n = jj_consume_token(Bool);
t1 = new ASTBool(Boolean.parseBoolean(n.image));
      break;
      }
    case QUOTE:{
      jj_consume_token(QUOTE);
      n = jj_consume_token(Id);
      jj_consume_token(QUOTE);
t1 = new ASTString(n.image);
      break;
      }
    case LPAR:{
      jj_consume_token(LPAR);
      t1 = EM();
      jj_consume_token(RPAR);
      break;
      }
    case MINUS:{
      jj_consume_token(MINUS);
      t1 = F();
t1=new ASTInvertInt(t1);
      break;
      }
    case NOT:{
      jj_consume_token(NOT);
      t1 = F();
t1=new ASTInvertBool(t1);
      break;
      }
    case PLUS:{
      jj_consume_token(PLUS);
      t1 = F();
      break;
      }
    case Id:{
      n = jj_consume_token(Id);
t1 = new ASTId(n.image);
      break;
      }
    case LET:{
      jj_consume_token(LET);
      label_7:
      while (true) {
        n = jj_consume_token(Id);
        jj_consume_token(COLON);
        t3 = TY();
        jj_consume_token(EQUAL);
        t1 = EM();
t2=map.put(n.image, t1);
          mapType.put(n.image, t3);
          if(t2!=null)
            {if (true) throw new TypeError(String.format("Id (%s) assigned twice at same level!", n.image));}
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case Id:{
          ;
          break;
          }
        default:
          jj_la1[12] = jj_gen;
          break label_7;
        }
      }
      jj_consume_token(IN);
      t2 = EM();
      jj_consume_token(END);
t1 = new ASTLet(
          map,
          mapType,
          t2);
      break;
      }
    case NEW:{
      jj_consume_token(NEW);
      t1 = F();
t1=new ASTRef(t1);
      break;
      }
    case DEASSIGN:{
      jj_consume_token(DEASSIGN);
      t1 = F();
t1=new ASTDeRef(t1);
      break;
      }
    case IF:{
      jj_consume_token(IF);
      t1 = EM();
      jj_consume_token(THEN);
      t2 = EM();
      jj_consume_token(ELSE);
      t3 = EM();
      jj_consume_token(END);
t1 = new ASTIf(t1, t2, t3);
      break;
      }
    case WHILE:{
      jj_consume_token(WHILE);
      t1 = EM();
      jj_consume_token(DO);
      t2 = EM();
      jj_consume_token(END);
t1 = new ASTWhile(t1, t2);
      break;
      }
    case PRINTLN:{
      jj_consume_token(PRINTLN);
      t1 = E();
t1 = new ASTPrintln(t1);
      break;
      }
    case FUN:{
      jj_consume_token(FUN);
      mapType = PL();
      jj_consume_token(APPLY);
      t1 = EM();
      jj_consume_token(END);
t1 = new ASTFunc(t1, mapType);
      break;
      }
    case LCBRACKET:{
      jj_consume_token(LCBRACKET);
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case Id:{
        n = jj_consume_token(Id);
        jj_consume_token(EQUAL);
        t1 = E();
        label_8:
        while (true) {
          switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
          case SEMICOLON:{
            ;
            break;
            }
          default:
            jj_la1[13] = jj_gen;
            break label_8;
          }
          jj_consume_token(SEMICOLON);
          a = jj_consume_token(Id);
          jj_consume_token(EQUAL);
          t2 = E();
if(map.put(a.image, t2)!=null)
            {if (true) throw new TypeError(String.format("Id (%s) assigned twice at same level!", n.image));}
        }
if(map.put(n.image, t1)!=null)
            {if (true) throw new TypeError(String.format("Id (%s) assigned twice at same level!", n.image));}
        break;
        }
      default:
        jj_la1[14] = jj_gen;
        ;
      }
t1 = new ASTStruct(map);
      jj_consume_token(RCBRACKET);
      break;
      }
    default:
      jj_la1[15] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
{if ("" != null) return t1;}
    throw new Error("Missing return statement in function");
  }

  static private boolean jj_2_1(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_1(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(0, xla); }
  }

  static private boolean jj_2_2(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_2(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(1, xla); }
  }

  static private boolean jj_2_3(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_3(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(2, xla); }
  }

  static private boolean jj_2_4(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_4(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(3, xla); }
  }

  static private boolean jj_2_5(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_5(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(4, xla); }
  }

  static private boolean jj_3R_16()
 {
    if (jj_scan_token(QUOTE)) return true;
    return false;
  }

  static private boolean jj_3R_15()
 {
    if (jj_scan_token(Bool)) return true;
    return false;
  }

  static private boolean jj_3R_14()
 {
    if (jj_scan_token(Num)) return true;
    return false;
  }

  static private boolean jj_3_3()
 {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_scan_token(27)) {
    jj_scanpos = xsp;
    if (jj_scan_token(28)) {
    jj_scanpos = xsp;
    if (jj_scan_token(25)) {
    jj_scanpos = xsp;
    if (jj_scan_token(26)) {
    jj_scanpos = xsp;
    if (jj_scan_token(23)) {
    jj_scanpos = xsp;
    if (jj_scan_token(29)) {
    jj_scanpos = xsp;
    if (jj_scan_token(30)) return true;
    }
    }
    }
    }
    }
    }
    if (jj_3R_10()) return true;
    return false;
  }

  static private boolean jj_3_2()
 {
    if (jj_scan_token(SEMICOLON)) return true;
    return false;
  }

  static private boolean jj_3R_13()
 {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3R_14()) {
    jj_scanpos = xsp;
    if (jj_3R_15()) {
    jj_scanpos = xsp;
    if (jj_3R_16()) {
    jj_scanpos = xsp;
    if (jj_3R_17()) {
    jj_scanpos = xsp;
    if (jj_3R_18()) {
    jj_scanpos = xsp;
    if (jj_3R_19()) {
    jj_scanpos = xsp;
    if (jj_3R_20()) {
    jj_scanpos = xsp;
    if (jj_3R_21()) {
    jj_scanpos = xsp;
    if (jj_3R_22()) {
    jj_scanpos = xsp;
    if (jj_3R_23()) {
    jj_scanpos = xsp;
    if (jj_3R_24()) {
    jj_scanpos = xsp;
    if (jj_3R_25()) {
    jj_scanpos = xsp;
    if (jj_3R_26()) {
    jj_scanpos = xsp;
    if (jj_3R_27()) {
    jj_scanpos = xsp;
    if (jj_3R_28()) {
    jj_scanpos = xsp;
    if (jj_3R_29()) return true;
    }
    }
    }
    }
    }
    }
    }
    }
    }
    }
    }
    }
    }
    }
    }
    return false;
  }

  static private boolean jj_3R_29()
 {
    if (jj_scan_token(LCBRACKET)) return true;
    return false;
  }

  static private boolean jj_3R_28()
 {
    if (jj_scan_token(FUN)) return true;
    return false;
  }

  static private boolean jj_3R_12()
 {
    if (jj_3R_10()) return true;
    return false;
  }

  static private boolean jj_3R_27()
 {
    if (jj_scan_token(PRINTLN)) return true;
    return false;
  }

  static private boolean jj_3_1()
 {
    if (jj_scan_token(SEMICOLON)) return true;
    if (jj_3R_9()) return true;
    return false;
  }

  static private boolean jj_3R_26()
 {
    if (jj_scan_token(WHILE)) return true;
    return false;
  }

  static private boolean jj_3R_11()
 {
    if (jj_3R_13()) return true;
    return false;
  }

  static private boolean jj_3R_25()
 {
    if (jj_scan_token(IF)) return true;
    return false;
  }

  static private boolean jj_3R_24()
 {
    if (jj_scan_token(DEASSIGN)) return true;
    return false;
  }

  static private boolean jj_3R_23()
 {
    if (jj_scan_token(NEW)) return true;
    return false;
  }

  static private boolean jj_3R_9()
 {
    if (jj_3R_12()) return true;
    return false;
  }

  static private boolean jj_3_4()
 {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_scan_token(47)) {
    jj_scanpos = xsp;
    if (jj_scan_token(48)) return true;
    }
    if (jj_3R_10()) return true;
    return false;
  }

  static private boolean jj_3_5()
 {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_scan_token(49)) {
    jj_scanpos = xsp;
    if (jj_scan_token(50)) return true;
    }
    if (jj_3R_11()) return true;
    return false;
  }

  static private boolean jj_3R_10()
 {
    if (jj_3R_11()) return true;
    return false;
  }

  static private boolean jj_3R_22()
 {
    if (jj_scan_token(LET)) return true;
    return false;
  }

  static private boolean jj_3R_21()
 {
    if (jj_scan_token(Id)) return true;
    return false;
  }

  static private boolean jj_3R_20()
 {
    if (jj_scan_token(PLUS)) return true;
    return false;
  }

  static private boolean jj_3R_19()
 {
    if (jj_scan_token(NOT)) return true;
    return false;
  }

  static private boolean jj_3R_18()
 {
    if (jj_scan_token(MINUS)) return true;
    return false;
  }

  static private boolean jj_3R_17()
 {
    if (jj_scan_token(LPAR)) return true;
    return false;
  }

  static private boolean jj_initialized_once = false;
  /** Generated Token Manager. */
  static public ParserTokenManager token_source;
  static SimpleCharStream jj_input_stream;
  /** Current token. */
  static public Token token;
  /** Next token. */
  static public Token jj_nt;
  static private int jj_ntk;
  static private Token jj_scanpos, jj_lastpos;
  static private int jj_la;
  static private int jj_gen;
  static final private int[] jj_la1 = new int[16];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static {
      jj_la1_init_0();
      jj_la1_init_1();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x8009c0e0,0x7e800000,0x0,0x0,0x400000,0x0,0x8009c020,0x0,0x0,0x0,0x3e00,0x3e00,0x0,0x0,0x0,0x8009c020,};
   }
   private static void jj_la1_init_1() {
      jj_la1_1 = new int[] {0x9f039,0x0,0x18000,0x60000,0x80004,0x80,0x9f039,0x80,0x2000,0x80,0x80000,0x80000,0x2000,0x200,0x2000,0x9f039,};
   }
  static final private JJCalls[] jj_2_rtns = new JJCalls[5];
  static private boolean jj_rescan = false;
  static private int jj_gc = 0;

  /** Constructor with InputStream. */
  public Parser(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public Parser(java.io.InputStream stream, String encoding) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser.  ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new ParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 16; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  static public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  static public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 16; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor. */
  public Parser(java.io.Reader stream) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser. ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new ParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 16; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  static public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 16; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor with generated Token Manager. */
  public Parser(ParserTokenManager tm) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser. ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 16; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(ParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 16; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  static private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      if (++jj_gc > 100) {
        jj_gc = 0;
        for (int i = 0; i < jj_2_rtns.length; i++) {
          JJCalls c = jj_2_rtns[i];
          while (c != null) {
            if (c.gen < jj_gen) c.first = null;
            c = c.next;
          }
        }
      }
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }

  @SuppressWarnings("serial")
  static private final class LookaheadSuccess extends java.lang.Error { }
  static final private LookaheadSuccess jj_ls = new LookaheadSuccess();
  static private boolean jj_scan_token(int kind) {
    if (jj_scanpos == jj_lastpos) {
      jj_la--;
      if (jj_scanpos.next == null) {
        jj_lastpos = jj_scanpos = jj_scanpos.next = token_source.getNextToken();
      } else {
        jj_lastpos = jj_scanpos = jj_scanpos.next;
      }
    } else {
      jj_scanpos = jj_scanpos.next;
    }
    if (jj_rescan) {
      int i = 0; Token tok = token;
      while (tok != null && tok != jj_scanpos) { i++; tok = tok.next; }
      if (tok != null) jj_add_error_token(kind, i);
    }
    if (jj_scanpos.kind != kind) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) throw jj_ls;
    return false;
  }


/** Get the next Token. */
  static final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  static final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  static private int jj_ntk_f() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  static private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  static private int[] jj_expentry;
  static private int jj_kind = -1;
  static private int[] jj_lasttokens = new int[100];
  static private int jj_endpos;

  static private void jj_add_error_token(int kind, int pos) {
    if (pos >= 100) return;
    if (pos == jj_endpos + 1) {
      jj_lasttokens[jj_endpos++] = kind;
    } else if (jj_endpos != 0) {
      jj_expentry = new int[jj_endpos];
      for (int i = 0; i < jj_endpos; i++) {
        jj_expentry[i] = jj_lasttokens[i];
      }
      jj_entries_loop: for (java.util.Iterator<?> it = jj_expentries.iterator(); it.hasNext();) {
        int[] oldentry = (int[])(it.next());
        if (oldentry.length == jj_expentry.length) {
          for (int i = 0; i < jj_expentry.length; i++) {
            if (oldentry[i] != jj_expentry[i]) {
              continue jj_entries_loop;
            }
          }
          jj_expentries.add(jj_expentry);
          break jj_entries_loop;
        }
      }
      if (pos != 0) jj_lasttokens[(jj_endpos = pos) - 1] = kind;
    }
  }

  /** Generate ParseException. */
  static public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[53];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 16; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
          if ((jj_la1_1[i] & (1<<j)) != 0) {
            la1tokens[32+j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 53; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    jj_endpos = 0;
    jj_rescan_token();
    jj_add_error_token(0, 0);
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  static final public void enable_tracing() {
  }

  /** Disable tracing. */
  static final public void disable_tracing() {
  }

  static private void jj_rescan_token() {
    jj_rescan = true;
    for (int i = 0; i < 5; i++) {
    try {
      JJCalls p = jj_2_rtns[i];
      do {
        if (p.gen > jj_gen) {
          jj_la = p.arg; jj_lastpos = jj_scanpos = p.first;
          switch (i) {
            case 0: jj_3_1(); break;
            case 1: jj_3_2(); break;
            case 2: jj_3_3(); break;
            case 3: jj_3_4(); break;
            case 4: jj_3_5(); break;
          }
        }
        p = p.next;
      } while (p != null);
      } catch(LookaheadSuccess ls) { }
    }
    jj_rescan = false;
  }

  static private void jj_save(int index, int xla) {
    JJCalls p = jj_2_rtns[index];
    while (p.gen > jj_gen) {
      if (p.next == null) { p = p.next = new JJCalls(); break; }
      p = p.next;
    }
    p.gen = jj_gen + xla - jj_la; p.first = token; p.arg = xla;
  }

  static final class JJCalls {
    int gen;
    Token first;
    int arg;
    JJCalls next;
  }

}
