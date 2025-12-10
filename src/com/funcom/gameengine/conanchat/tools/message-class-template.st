package com.funcom.gameengine.conanchat.packets2;

$imports: {import $it$;}; separator="\n"$
import java.nio.ByteBuffer;

/**
 * References protocol.py:
 * <p/>
 * <code>$javadocreferenceline$</code>
 *
 * @author com.funcom.gameengine.conanchat.packets2.MessageGenerator;
 */
@Admin
@Client2Server
public class $classname$ implements ChatMessage {
  $messagedata: {private $it.aClass.simpleName$ $it.name$;}; separator="\n"$

  public $classname$() {
  }

  public $classname$($messagedata: {$it.aClass.simpleName$ $it.name$}; separator=", "$) {
    $messagedata: {this.$it.name$ = $it.name$;}; separator="\n"$
  }

  public $classname$(ByteBuffer byteBuffer) {
    $messagedata: {$it.name$ = new $it.aClass.simpleName$(byteBuffer, Endianess.BIG_ENDIAN);}; separator="\n"$   
  }

  @Override
  public short getMessageType() {
    return ConanChatMessageType.$messagetype$;
  }

  @Override
  public ChatMessage toMessage(ByteBuffer buffer) {
    return new $classname$(buffer);
  }

  @Override
  public int getSerializedSize() {
    return $messagedata: {$it.name$.getSizeInBytes()}; separator="\n\t\t\t\t+ "$;
  }

  @Override
  public ByteBuffer serialize(ByteBuffer buffer) {
    $messagedata: {$it.name$.toByteBuffer(buffer);}; separator="\n"$
    return buffer;
  }

  $messagedata: {public $it.aClass.simpleName$ get$it.uName$() {$\n$$\t$return $it.name$;$\n$}}; separator="\n\n"$
}
