/*******************************************************************************
 * Copyright (c) 2002,2006 IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package com.ibm.wala.shrikeBT;

/**
 * This class represents the ?astore instructions.
 */
final public class ArrayStoreInstruction extends Instruction {
  protected ArrayStoreInstruction(short opcode) {
    this.opcode = opcode;
  }

  private final static ArrayStoreInstruction[] preallocated = preallocate();

  private static ArrayStoreInstruction[] preallocate() {
    ArrayStoreInstruction[] r = new ArrayStoreInstruction[OP_sastore - OP_iastore + 2];
    for (short i = OP_iastore; i <= OP_sastore; i++) {
      r[i - OP_iastore] = new ArrayStoreInstruction(i);
    }
    r[OP_sastore - OP_iastore + 1] = r[OP_baload - OP_iaload];
    return r;
  }

  public static ArrayStoreInstruction make(String type) throws IllegalArgumentException {
    int i = Util.getTypeIndex(type);
    if (i < 0 || i > TYPE_boolean_index) {
      throw new IllegalArgumentException("Invalid type " + type + " for ArrayStoreInstruction");
    }
    return preallocated[i];
  }

  public boolean equals(Object o) {
    if (o instanceof ArrayStoreInstruction) {
      ArrayStoreInstruction i = (ArrayStoreInstruction) o;
      return i.opcode == opcode;
    } else {
      return false;
    }
  }

  public int hashCode() {
    return opcode + 148791;
  }

  public int getPoppedCount() {
    return 3;
  }

  public String getType() {
    return Decoder.indexedTypes[opcode - OP_iastore];
  }

  public String toString() {
    return "ArrayStore(" + getType() + ")";
  }

  public void visit(Visitor v) throws NullPointerException {
    v.visitArrayStore(this);
  }
  /* (non-Javadoc)
   * @see com.ibm.domo.cfg.IInstruction#isPEI()
   */
  public boolean isPEI() {
    return true;
  }
}