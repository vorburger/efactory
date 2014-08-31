/*
 * #%L
 * org.eclipse.emf.eson.tests
 * %%
 * Copyright (C) 2013 - 2014 EclipseSource
 * %%
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompanies this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 * #L%
 *
 * Contributors:
 *    Ralf Sternberg - initial implementation and API
 */
package org.eclipse.emf.eson.generator.tests.eclipsesource_json;

import java.io.IOException;


@SuppressWarnings( "serial" ) // use default serial UID
class JsonString extends JsonValue {

  private final String string;

  JsonString( String string ) {
    if( string == null ) {
      throw new NullPointerException( "string is null" );
    }
    this.string = string;
  }

  @Override
  protected void write( JsonWriter writer ) throws IOException {
    writer.writeString( string );
  }

  @Override
  public boolean isString() {
    return true;
  }

  @Override
  public String asString() {
    return string;
  }

  @Override
  public int hashCode() {
    return string.hashCode();
  }

  @Override
  public boolean equals( Object object ) {
    if( this == object ) {
      return true;
    }
    if( object == null ) {
      return false;
    }
    if( getClass() != object.getClass() ) {
      return false;
    }
    JsonString other = (JsonString)object;
    return string.equals( other.string );
  }

}
