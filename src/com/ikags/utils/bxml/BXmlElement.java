package com.ikags.utils.bxml;

import java.util.Vector;
/**
 * xml��node�ڵ�,ת���ɵ����ݽṹ
 * @author zhangxiasheng
 *
 */
public class BXmlElement
{

  public BXmlElement()
  {
    children = new Vector<BXmlElement>();
  }

  public BXmlElement(String TagName)
  {
    if (TagName != null)
    {
      tagName = TagName;
    }
    children = new Vector<BXmlElement>();
  }

  private Vector<BXmlElement> children;
  private String[] attributesName;
  private String[] attributesValue;
  private String tagName;
  private String contents;
  public int id;
  /**
   * �˽ڵ�ĸ��ڵ�,�����root�ڵ���Ϊnull
   */
  public BXmlElement parent = null;
  public int parentID = 0;
  /**
   * �˽ڵ����ڵĸ��ڵ����һ���ڵ�,����ǵ�һ���ڵ���Ϊnull
   */
  public BXmlElement previous = null;
  public int previousID = 0;
  /**
   * �˽ڵ����ڵĸ��ڵ����һ���ڵ�,��������һ���ڵ���Ϊnull
   */
  public BXmlElement next = null;
  public int nextID = 0;

  /**
   * ��ʼ��attributes�Ĵ�С(���ɴ˽ڵ�ʱ����,һ�㲻��Ҫʹ�ô˷���)
   * 
   * @param size
   */
  public void setNewAttribute(int size)
  {
    attributesName = new String[size];
    attributesValue = new String[size];
  }

  /**
   * ����attributesName[index]��Name(���ɴ˽ڵ�ʱ����,һ�㲻��Ҫʹ�ô˷���)
   * 
   * @param index
   * @param Name
   */
  public void setAttributeName(int index, String Name)
  {
    if (index < 0 || index > attributesName.length - 1)
    {
      return;
    }
    attributesName[index] = Name;
  }

  /**
   * ����attributesValue[index]��name(���ɴ˽ڵ�ʱ����,һ�㲻��Ҫʹ�ô˷���)
   * 
   * @param index
   * @param Value
   */
  public void setAttributeValue(int index, String Value)
  {
    if (index < 0 || index >= attributesValue.length)
    {
      return;
    }
    attributesValue[index] = Value;
  }

  /**
   * ��ȡattributesָ��index��name
   * 
   * @param index
   * @return String
   */
  public String getAttributeName(int index)
  {
    if (attributesName == null)
    {
      return null;
    }
    if (index < 0 || index >= attributesName.length)
    {
      return null;
    }
    return attributesName[index];
  }

  /**
   * ��ȡattributesָ��index��Value
   * 
   * @param index
   * @return String
   */
  public String getAttributeValue(int index)
  {
    if (attributesValue == null)
    {
      return null;
    }
    if (index < 0 || index >= attributesValue.length)
    {
      return null;
    }
    return attributesValue[index];
  }

  /**
   * ��ȡattributes����Ŀ
   * 
   * @return int
   */
  public int getAttributeCounts()
  {
    if (attributesValue == null)
    {
      return 0;
    }
    return attributesValue.length;
  }

  /**
   * ��ȡattributesָ��name��Value
   * 
   * @param abName
   * @return String
   */
  public String getAttributeValue(String abName)
  {
    if (abName == null)
    {
      return null;
    }
    if (attributesName == null)
    {
      return null;
    }
    if (attributesValue == null)
    {
      return null;
    }
    for (int i = 0; i < attributesName.length; i++)
    {
      if (attributesName[i].equalsIgnoreCase(abName))
      {
        return attributesValue[i];
      }
    }
    return null;
  }

  public boolean removeAttributeValue(String abName)
  {
    if (abName == null)
    {
      return false;
    }
    if (attributesName == null)
    {
      return false;
    }
    if (attributesValue == null)
    {
      return false;
    }
    for (int i = 0; i < attributesName.length; i++)
    {
      if (attributesName[i].equalsIgnoreCase(abName))
      {
        if (attributesName.length == 1)
        {
          attributesName = null;
          attributesValue = null;
          return true;
        }
        int size = attributesName.length - 1;
        String[] tmpName = new String[size];
        String[] tmpValue = new String[size];
        if (size == 0)
        {
          attributesName = null;
          attributesValue = null;
        }
        else
        {
          int count = 0;
          for (int j = 0; j < tmpName.length; j++)
          {
            if (j == i)
            {
              count++;
            }
            tmpName[j] = attributesName[count];
            tmpValue[j] = attributesValue[count];
            count++;
          }
          attributesName = tmpName;
          attributesValue = tmpValue;
          return true;
        }
      }
    }
    return false;
  }

  /**
   * ��ȡ�˽ڵ��Vector
   * 
   * @return Vector
   */
  public Vector<BXmlElement> getChildren()
  {
    return this.children;
  }

  /**
   * ��ȡ�˽ڵ���ָ�����ӽڵ�
   * 
   * @param index
   * @return BXmlElement
   */
  public BXmlElement getChildrenElement(int index)
  {
    if (index < 0 || index >= children.size())
    {
      return null;
    }
    return ( BXmlElement ) children.elementAt(index);
  }

  /**
   * �ڴ˽ڵ�������һ���ӽڵ�
   * 
   * @param el
   * @return boolean
   */
  public boolean addChildrenElement(BXmlElement el)
  {
    children.addElement(el);
    return true;
  }

  /**
   * �ڴ˽ڵ�������һ���ӽڵ�
   * 
   * @param el
   * @return boolean
   */
  public boolean addChildrenElement(BXmlElement el, int index)
  {
    children.add(index, el);
    return true;
  }

  /**
   * ɾ���˽ڵ��µ�ָ���ӽڵ�
   * 
   * @param index
   * @return boolean
   */
  public boolean removeChildrenElement(int index)
  {
    if (index < 0 || index >= children.size())
    {
      return false;
    }
    children.removeElementAt(index);
    return true;
  }

  /**
   * ɾ���˽ڵ��µ������ӽڵ�
   * 
   * @return boolean
   */
  public boolean removeAllChildrenElements()
  {
    children.removeAllElements();
    return true;
  }

  /**
   * ��ȡContents
   * 
   * @return String
   */
  public String getContents()
  {
    return contents;
  }

  /**
   * ��Contents��ֵ
   * 
   * @param contents
   */
  public void setContents(String contents)
  {
    this.contents = contents;
  }

  /**
   * ��ȡTagName
   * 
   * @return String
   */
  public String getTagName()
  {
    return tagName;
  }

  /**
   * ��TagName��ֵ
   * 
   * @param tagName
   */
  public void setTagName(String tagName)
  {
    this.tagName = tagName;
  }

  public void addAttri(Vector<String> name, Vector<String> value)
  {
    if (attributesName == null)
    {
      this.setNewAttribute(name.size());
      for (int i = 0; i < name.size(); i++)
      {
        this.setAttributeName(i, name.elementAt(i));
        this.setAttributeValue(i, value.elementAt(i));
      }
    }
    else
    {
      // ��������,���������.�Ȱ�ԭ���ĸ��Ƶ�Vector����,Ȼ����������
      for (int i = 0; i < attributesName.length; i++)
      {
        name.add(attributesName[i]);
        value.add(attributesValue[i]);
      }
      this.setNewAttribute(name.size());
      for (int i = 0; i < name.size(); i++)
      {
        this.setAttributeName(i, name.elementAt(i));
        this.setAttributeValue(i, value.elementAt(i));
      }
    }
  }

  /**
   * ��ӡ�Դ�elementΪroot��������
   */
  // public void print(int indentNum)
  // {
  // for (int i = 0; i < indentNum; i++)
  // {
  // System.out.print("____");
  // }
  // if ("".equals(tagName))
  // {
  // if (contents != null)
  // {
  // System.out.println(contents);
  // }
  // }
  // else
  // {
  // System.out.print("<" + tagName + " ");
  // if (attributesName != null)
  // {
  // for (int i = 0; i < attributesName.length; i++)
  // {
  // System.out.print(attributesName[i] + "=\"" + attributesValue[i] + "\" ");
  // }
  // }
  // if (children.size() == 0)
  // {
  // System.out.println("/>");
  // }
  // else
  // {
  // System.out.println(">");
  // for (int i = 0; i < children.size(); i++)
  // {
  // BXmlElement elt = ( BXmlElement ) children.elementAt(i);
  // elt.print(indentNum + 1);
  // }
  // for (int i = 0; i < indentNum; i++)
  // {
  // System.out.print("____");
  // }
  // System.out.println("</" + tagName + ">");
  // }
  // }
  // }
  /**
   * ��ӡ�Դ�elementΪroot��������
   */
  public void printNode(int indentNum)
  {
    for (int i = 0; i < indentNum; i++)
    {
      System.out.print("    ");
    }
    if ("".equals(tagName) && contents != null)
    {
      System.out.println(contents);
    }
    else
    {
      System.out.print("������<" + tagName + "> ");
      if (attributesName != null)
      {
        for (int i = 0; i < attributesName.length; i++)
        {
          System.out.print(attributesName[i] + "=\"" + attributesValue[i] + "\", ");
        }
      }
      System.out.println("");
      if (children.size() > 0)
      {
        for (int i = 0; i < children.size(); i++)
        {
          BXmlElement element = ( BXmlElement ) children.elementAt(i);
          element.printNode(indentNum + 1);
        }
      }
    }
  }
  
  
  public void printNodeBuffer(StringBuffer sb,int indentNum)
  {
    for (int i = 0; i < indentNum; i++)
    {
    // System.out.print("    ");
      sb.append("    ");
    }
    if ("".equals(tagName) && contents != null)
    {
      //System.out.println(contents);
      sb.append(contents);
      sb.append("\n");
    }
    else
    {
      //System.out.print("������<" + tagName + "> ");
      sb.append("������<" + tagName + "> ");
      if (attributesName != null)
      {
        for (int i = 0; i < attributesName.length; i++)
        {
         // System.out.print(attributesName[i] + "=\"" + attributesValue[i] + "\", ");
            sb.append(attributesName[i] + "=\"" + attributesValue[i] + "\", ");
        }
      }
     // System.out.println("");
      sb.append("\n");
      if (children.size() > 0)
      {
        for (int i = 0; i < children.size(); i++)
        {
          BXmlElement element = ( BXmlElement ) children.elementAt(i);
          element.printNodeBuffer(sb,indentNum + 1);
        }
      }
    }
  }
  

  /**
   * ���л�������
   */
  public String elementToString()
  {
    StringBuffer sbu = new StringBuffer();
    if ("".equals(tagName))
    {
      if (contents != null)
      {
        sbu.append(contents);
      }
    }
    else
    {
      sbu.append("<" + tagName + " ");
      if (attributesName != null)
      {
        for (int i = 0; i < attributesName.length; i++)
        {
          sbu.append(attributesName[i] + "=\"" + attributesValue[i] + "\" ");
        }
      }
      if (children.size() == 0)
      {
        sbu.append("/>");
      }
      else
      {
        sbu.append(">");
        for (int i = 0; i < children.size(); i++)
        {
          BXmlElement elt = ( BXmlElement ) children.elementAt(i);
          sbu.append(elt.elementToString());
        }
        sbu.append("</" + tagName + ">");
      }
    }
    return sbu.toString();
  }

  /**
   * ���л� BXmlElement
   * 
   * @return byte[]
   */
  public byte[] serialize()
  {
    String str = new String(this.elementToString());
    return str.getBytes();
  }

  /**
   * �����л�
   * 
   * @param byteArray
   * @return BXmlElement
   */
  public static BXmlElement deserialize(byte[] byteArray)
  {
    BXmlElement bxmle = new BXmlElement();
    String str = new String(byteArray);
    bxmle = BXmlDriver.loadXML(str);
    return bxmle;
  }

  /**
   * �ж����Լ�Ϊroot����,�Ƿ��д˽ڵ�
   * 
   * @param Element
   * @return boolean
   */
  public boolean isMyChildren(BXmlElement Element)
  {
    int size = this.children.size();
    for (int i = 0; i < size; i++)
    {
      BXmlElement ele = ( BXmlElement ) this.children.elementAt(i);
      if (ele.equals(Element))
      {
        return true;
      }
      int childrensize = ele.children.size();
      if (childrensize > 0)
      {
        boolean istrue = ele.isMyChildren(Element);
        if (istrue == true)
        {
          return true;
        }
        else
        {
          // ��������
        }
      }
    }
    return false;
  }

  public int isMyTag(String[] Tags)
  {
    int size = this.children.size();
    for (int i = 0; i < size; i++)
    {
      BXmlElement ele = ( BXmlElement ) this.children.elementAt(i);
      for (int j = 0; j < Tags.length; j++)
      {
        if (ele.getTagName().equalsIgnoreCase(Tags[j]))
        {
          return j;
        }
      }
      int childrensize = ele.children.size();
      if (childrensize > 0)
      {
        int isTag = ele.isMyTag(Tags);
        if (isTag != -1)
        {
          return isTag;
        }
        else
        {
          // ��������
        }
      }
    }
    return -1;
  }

  public BXmlElement getElement(String Tag)
  {
    if (this.getTagName().equalsIgnoreCase(Tag))
    {
      return this;
    }
    int size = this.children.size();
    for (int i = 0; i < size; i++)
    {
      BXmlElement ele = ( BXmlElement ) this.children.elementAt(i);
      if (ele.getTagName().equalsIgnoreCase(Tag))
      {
        return ele;
      }
      int childrensize = ele.children.size();
      if (childrensize > 0)
      {
        BXmlElement isTag = ele.getElement(Tag);
        if (isTag != null)
        {
          return isTag;
        }
        else
        {
          // ��������
        }
      }
    }
    return null;
  }
  
  
  public String getChildContents() {
  try {
      BXmlElement bxml = this.getChildrenElement(0);
      String contents=null;
      if(bxml!=null){
        contents = bxml.getContents();
      }
      return contents;
  } catch (Exception ex) {
      ex.printStackTrace();
  }
  return null;
  }
  
}
