package psforever.net

import scodec.{Err, DecodeResult, Attempt, Codec}
import scodec.bits.BitVector
import scodec.codecs._

object GamePacketOpcode extends Enumeration {
  type Type = Value
  val

  // Opcodes should have a marker every 10 (decimal)
  // OPCODE 0
  Unknown0,
  LoginMessage,
  LoginRespMessage,
  Unknown3,
  ConnectToWorldMessage,
  VNLWorldStatusMessage,
  UnknownMessage6,
  UnknownMessage7,
  PlayerStateMessage,
  UnknownMessage9,

  // OPCODE 10
  HitHint,
  DamageMessage,
  DestroyMessage,
  ReloadMessage,
  MountVehicleMsg,
  DismountVehicleMsg
  = Value

  def getPacketDecoder(opcode : GamePacketOpcode.Type) : (BitVector) => Attempt[DecodeResult[PlanetSideGamePacket]] = opcode match {
    case LoginMessage => psforever.net.LoginMessage.decode
    case LoginRespMessage => psforever.net.LoginRespMessage.decode
    case VNLWorldStatusMessage => psforever.net.VNLWorldStatusMessage.decode
    case default => (a : BitVector) => Attempt.failure(Err(s"Could not find a marshaller for game packet ${opcode}"))
  }

  implicit val codec: Codec[this.Value] = PacketHelpers.createEnumerationCodec(this, uint8L)
}