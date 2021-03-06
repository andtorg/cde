/*!
* Copyright 2002 - 2014 Webdetails, a Pentaho company.  All rights reserved.
*
* This software was developed by Webdetails and is provided under the terms
* of the Mozilla Public License, Version 2.0, or any later version. You may not use
* this file except in compliance with the license. If you need a copy of the license,
* please go to  http://mozilla.org/MPL/2.0/. The Initial Developer is Webdetails.
*
* Software distributed under the Mozilla Public License is distributed on an "AS IS"
* basis, WITHOUT WARRANTY OF ANY KIND, either express or  implied. Please refer to
* the license for the specific language governing your rights and limitations.
*/

package pt.webdetails.cdf.dd.api;

import pt.webdetails.cdf.dd.CdeSettings;
import pt.webdetails.cdf.dd.CdeVersionChecker;
import pt.webdetails.cdf.dd.util.JsonUtils;
import pt.webdetails.cpf.VersionChecker;
import pt.webdetails.cpf.VersionChecker.CheckVersionResponse;
import pt.webdetails.cpf.annotations.AccessLevel;
import pt.webdetails.cpf.annotations.Exposed;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import java.io.IOException;

@Path( "pentaho-cdf-dd/api/version" )
public class VersionApi {

  @GET
  @Path( "/check" )
  @Produces( "text/plain" )
  public void checkVersion( @Context HttpServletResponse response ) throws IOException {
    VersionChecker versionChecker = new CdeVersionChecker( CdeSettings.getSettings() );
    CheckVersionResponse result = versionChecker.checkVersion();
    JsonUtils.buildJsonResult( response.getOutputStream(), result != null, result );
  }

  @GET
  @Path( "/get" )
  @Produces( "text/plain" )
  @Exposed( accessLevel = AccessLevel.PUBLIC )
  public String getVersion() {
    VersionChecker versionChecker = new CdeVersionChecker( CdeSettings.getSettings() );
    return versionChecker.getVersion();
  }
}
