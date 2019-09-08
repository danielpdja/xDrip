package com.eveningoutpost.dexdrip.watch.thinjam.firmware;

import com.eveningoutpost.dexdrip.Models.JoH;
import com.eveningoutpost.dexdrip.Models.UserError;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;

import lombok.val;

// jamorham

public class DigitalSignature {

    private static final String OTA_FILE_SIGNING_PUBLIC_KEY = "308203433082023506072A8648CE3804013082022802820101008F" +
            "7935D9B9AAE9BFABED887ACF4951B6F32EC59E3BAF3718E8EAC4961F3EFD3606E74351A9C4183339B809E7C2AE1C539BA7475B85" +
            "D011ADB8B47987754984695CAC0E8F14B3360828A22FFA27110A3D62A993453409A0FE696C4658F84BDD20819C3709A01057B195" +
            "ADCD00233DBA5484B6291F9D648EF883448677979CEC04B434A6AC2E75E9985DE23DB0292FC1118C9FFA9D8181E7338DB792B730" +
            "D7B9E349592F68099872153915EA3D6B8B4653C633458F803B32A4C2E0F27290256E4E3F8A3B0838A1C450E4E18C1A29A37DDF5E" +
            "A143DE4B66FF04903ED5CF1623E158D487C608E97F211CD81DCA23CB6E380765F822E342BE484C05763939601CD667021D00BAF6" +
            "96A68578F7DFDEE7FA67C977C785EF32B233BAE580C0BCD5695D0282010016A65C58204850704E7502A39757040D34DA3A3478C1" +
            "54D4E4A5C02D242EE04F96E61E4BD0904ABDAC8F37EEB1E09F3182D23C9043CB642F88004160EDF9CA09B32076A79C32A627F247" +
            "3E91879BA2C4E744BD2081544CB55B802C368D1FA83ED489E94E0FA0688E32428A5C78C478C68D0527B71C9A3ABB0B0BE12C4468" +
            "9639E7D3CE74DB101A65AA2B87F64C6826DB3EC72F4B5599834BB4EDB02F7C90E9A496D3A55D535BEBFC45D4F619F63F3DEDBB87" +
            "3925C2F224E07731296DA887EC1E4748F87EFB5FDEB75484316B2232DEE553DDAF02112B0D1F02DA30973224FE27AEDA8B9D4B29" +
            "22D9BA8BE39ED9E103A63C52810BC688B7E2ED4316E1EF17DBDE03820106000282010100818027661586A23645DD7FD760F4E993" +
            "E7016924A4764ED06132393F10F1273AE1D81829371A6E3B72891036F0CF998D8FFFB40F7EB783A8F9C3A8088AE016F290F44E1F" +
            "67DE01019C7B1B9E22D9461DBB73F6CD8F9F14635B4529D59303290613DC223FB34B1B0F9F96800B0A8D97711814A4D94654FE32" +
            "E7BFEDB126D1DB7B3FD6D2BE37C048529373AA052EFDB596CBFD93363792CC443A22AEB25C43C3E47DE0D884E2931FD9CD498576" +
            "57AAFC0722BF15999D8EA397F487739B56DF651EA47FB39AB6AA8BE4B3E0843B930F7AC0F50D16AFE85130BCDDB44232F0DBD257" +
            "48EEE5CAB7C5DE9CACC62CA8AA1860E2110C050F37873E0D8534CCCBA3D5CB8A";


    private static final String TAG = "BlueJaySignature";

    private static PublicKey getPublicKey() {
        try {
            return KeyFactory.getInstance("DSA")
                    .generatePublic(new X509EncodedKeySpec(JoH.hexStringToByteArray(OTA_FILE_SIGNING_PUBLIC_KEY)));
        } catch (Exception e) {
            UserError.Log.e(TAG, "Failed to get public key: " + e);
            return null;
        }
    }

    public static boolean firstCheck(final byte[] data, final byte[] signature) {
        try {
            val signCheck = Signature.getInstance("SHA256withDSA");
            signCheck.initVerify(getPublicKey());
            signCheck.update(data);
            return signCheck.verify(signature);
        } catch (Exception e) {
            UserError.Log.e(TAG, "Verification failed due to exception: " + e);
            return false;
        }
    }

}

