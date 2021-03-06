/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/. */

package pt.webdetails.cdf.dd.model.meta.writer.cderunjs;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import pt.webdetails.cdf.dd.model.core.Thing;
import pt.webdetails.cdf.dd.model.meta.ComponentType;
import pt.webdetails.cdf.dd.model.meta.MetaModel;
import pt.webdetails.cdf.dd.model.meta.PropertyType;
import pt.webdetails.cdf.dd.model.core.UnsupportedThingException;
import pt.webdetails.cdf.dd.model.core.writer.IThingWriteContext;
import pt.webdetails.cdf.dd.model.core.writer.IThingWriter;
import pt.webdetails.cdf.dd.model.core.writer.IThingWriterFactory;
import pt.webdetails.cdf.dd.model.core.writer.ThingWriteException;
import pt.webdetails.cdf.dd.model.core.writer.js.JsWriterAbstract;

/**
 * @author dcleao
 */
public class CdeRunJsModelWriter extends JsWriterAbstract implements IThingWriter
{
  protected static final Log _logger = LogFactory.getLog(CdeRunJsModelWriter.class);

  public void write(java.lang.Object output, IThingWriteContext context, Thing t)
      throws ThingWriteException
  {
    MetaModel model = (MetaModel)t;
    StringBuilder out = (StringBuilder)output;

    IThingWriterFactory factory = context.getFactory();
    assert factory != null;

    // GLOBAL PROPERTIES
    for(PropertyType prop : model.getPropertyTypes())
    {
      IThingWriter propWriter;
      try
      {
        propWriter = factory.getWriter(prop);
        assert propWriter != null;
      }
      catch(UnsupportedThingException ex)
      {
        ThingWriteException ex2 = new ThingWriteException(ex);
        if(context.getBreakOnError()) { throw ex2; }
        _logger.error(ex2);
        continue;
      }

      propWriter.write(out, context, prop);
    }

    // COMPONENTS
    // Components output their own properties
    for(ComponentType comp : model.getComponentTypes())
    {
      IThingWriter compWriter;
      try
      {
        compWriter = factory.getWriter(comp);
        assert compWriter != null;
      }
      catch(UnsupportedThingException ex)
      {
        ThingWriteException ex2 = new ThingWriteException(ex);
        if(context.getBreakOnError()) { throw ex2; }
        _logger.error(ex2);
        continue;
      }

      compWriter.write(out, context, comp);
    }
  }
}