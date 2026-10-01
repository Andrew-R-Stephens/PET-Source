package com.tritiumgaming.data.challenge.mappers

import com.tritiumgaming.core.resources.R
import com.tritiumgaming.data.challenge.mapper.ChallengeResources.ChallengeDescription
import com.tritiumgaming.data.challenge.mapper.ChallengeResources.ChallengeTitle

fun ChallengeTitle.toStringResource() =
    when(this) {
        ChallengeTitle.LIGHTS_OUT -> R.string.challenge_lights_out_title
        ChallengeTitle.SPEED_DEMONS -> R.string.challenge_speed_demons_title
        ChallengeTitle.DETECTIVES_ONLY -> R.string.challenge_detectives_only_title
        ChallengeTitle.HIDE_AND_SEEK_SEEKER -> R.string.challenge_hide_and_seek_seeker_title
        ChallengeTitle.HIDE_AND_SEEK_HIDE -> R.string.challenge_hide_and_seek_hide_title
        ChallengeTitle.FROSTBITTEN -> R.string.challenge_frostbitten_title
        ChallengeTitle.DO_AS_I_COMMAND -> R.string.challenge_do_as_i_command_title
        ChallengeTitle.TORTOISE_AND_THE_HARE_HARE -> R.string.challenge_tortoise_and_the_hare_hare_title
        ChallengeTitle.TORTOISE_AND_THE_HARE_TORTOISE -> R.string.challenge_tortoise_and_the_hare_tortoise_title
        ChallengeTitle.GOTTA_GO_FAST -> R.string.challenge_gotta_go_fast_title
        ChallengeTitle.SANITY_SURVIVAL -> R.string.challenge_sanity_survival_title
        ChallengeTitle.SPEEDRUN -> R.string.challenge_speedrun_title
        ChallengeTitle.SURVIVAL_OF_THE_FITTEST -> R.string.challenge_survival_of_the_fittest_title
        ChallengeTitle.PRIMITIVE -> R.string.challenge_primitive_title
        ChallengeTitle.VULNERABLE -> R.string.challenge_vulnerable_title
        ChallengeTitle.MISSED_DELIVERY -> R.string.challenge_missed_delivery_title
        ChallengeTitle.AUDIO_ONLY -> R.string.challenge_audio_only_title
        ChallengeTitle.TECHNOPHILIA -> R.string.challenge_technophilia_title
        ChallengeTitle.NO_EVIDENCE -> R.string.challenge_no_evidence_title
        ChallengeTitle.THE_APOCALYPSE_DRAWS_NEAR -> R.string.challenge_the_apocalypse_draws_near_title
        ChallengeTitle.SLOW_AND_STEADY -> R.string.challenge_slow_and_steady_title
        ChallengeTitle.PARANORMAL_PAPARAZZI -> R.string.challenge_paranormal_paparazzi_title
        ChallengeTitle.HIDE_AND_SEEK_EXTREME -> R.string.challenge_hide_and_seek_extreme_title
        ChallengeTitle.GLOW_IN_THE_DARK -> R.string.challenge_glow_in_the_dark_title
        ChallengeTitle.DEJA_VU -> R.string.challenge_deja_vu_title
        ChallengeTitle.TAG_YOURE_IT -> R.string.challenge_tag_youre_it_title
    }

fun ChallengeDescription.toStringResource() =
    when(this) {
        ChallengeDescription.LIGHTS_OUT -> R.string.challenge_lights_out_description
        ChallengeDescription.SPEED_DEMONS -> R.string.challenge_speed_demons_description
        ChallengeDescription.DETECTIVES_ONLY -> R.string.challenge_detectives_only_description
        ChallengeDescription.HIDE_AND_SEEK_SEEKER -> R.string.challenge_hide_and_seek_seeker_description
        ChallengeDescription.HIDE_AND_SEEK_HIDE -> R.string.challenge_hide_and_seek_hide_description
        ChallengeDescription.FROSTBITTEN -> R.string.challenge_frostbitten_description
        ChallengeDescription.DO_AS_I_COMMAND -> R.string.challenge_do_as_i_command_description
        ChallengeDescription.TORTOISE_AND_THE_HARE_HARE -> R.string.challenge_tortoise_and_the_hare_hare_description
        ChallengeDescription.TORTOISE_AND_THE_HARE_TORTOISE -> R.string.challenge_tortoise_and_the_hare_tortoise_description
        ChallengeDescription.GOTTA_GO_FAST -> R.string.challenge_gotta_go_fast_description
        ChallengeDescription.SANITY_SURVIVAL -> R.string.challenge_sanity_survival_description
        ChallengeDescription.SPEEDRUN -> R.string.challenge_speedrun_description
        ChallengeDescription.SURVIVAL_OF_THE_FITTEST -> R.string.challenge_survival_of_the_fittest_description
        ChallengeDescription.PRIMITIVE -> R.string.challenge_primitive_description
        ChallengeDescription.VULNERABLE -> R.string.challenge_vulnerable_description
        ChallengeDescription.MISSED_DELIVERY -> R.string.challenge_missed_delivery_description
        ChallengeDescription.AUDIO_ONLY -> R.string.challenge_audio_only_description
        ChallengeDescription.TECHNOPHILIA -> R.string.challenge_technophilia_description
        ChallengeDescription.NO_EVIDENCE -> R.string.challenge_no_evidence_description
        ChallengeDescription.THE_APOCALYPSE_DRAWS_NEAR -> R.string.challenge_the_apocalypse_draws_near_description
        ChallengeDescription.SLOW_AND_STEADY -> R.string.challenge_slow_and_steady_description
        ChallengeDescription.PARANORMAL_PAPARAZZI -> R.string.challenge_paranormal_paparazzi_description
        ChallengeDescription.HIDE_AND_SEEK_EXTREME -> R.string.challenge_hide_and_seek_extreme_description
        ChallengeDescription.GLOW_IN_THE_DARK -> R.string.challenge_glow_in_the_dark_description
        ChallengeDescription.DEJA_VU -> R.string.challenge_deja_vu_description
        ChallengeDescription.TAG_YOURE_IT -> R.string.challenge_tag_youre_it_description
    }
