/*
 * 
 * From BungeeCord by md_5 (https://github.com/SpigotMC/BungeeCord)
 * 
 */
package com.vivi7865.Network.Manager.log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.common.base.Charsets;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LoggingOutputStream extends ByteArrayOutputStream
{

    private static final String separator = System.getProperty( "line.separator" );
    /*========================================================================*/
    private final Logger logger;
    private final Level level;

    @Override
    public void flush() throws IOException
    {
        String contents = toString( Charsets.UTF_8.name() );
        super.reset();
        if ( !contents.isEmpty() && !contents.equals( separator ) )
        {
            logger.logp( level, "", "", contents );
        }
    }
}