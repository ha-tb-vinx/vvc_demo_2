#!/bin/csh
#===============================================================================
#	�W���uID�@�@�F�@URIB061
#	�t�@�C�����@�F�@TanpinMeisaiDataSummary.sh
#	�����@�@�@�@�F�@
#	�������e�@�@�F�@�i���׏��W�v����
#	�쐬�ҁ@�@�@�F�@�V��
#	�쐬���@�@�@�F�@2009.05.26
#	�X�V�����@�@�F�@2009.07.03 VJC.���i ����csv�̏o�͐�w��ύX�ׁ̈A�����ǉ�
#	�@�@�@�@�@�@�F�@2009.07.04 VJC.���i �t�@�C���o�b�N�A�b�v�����ǉ�
#===============================================================================

# ���ʕϐ��Ǎ�
source ./CommonEnv.sh

# �W���uID�Z�b�g
set JOB_ID=URIB061
set EXE_MACRO_PATH=$MACRO_PATH/$JOB_ID

# ���O�t�@�C�����Z�b�g
set LOG_FILE=$LOG_PATH/$JOB_ID.log

# �����}�N�����Z�b�g
set SHIN_TANPIN_MEISAI_DATA_IMP=ShinTanpinMeisaiDataImport.opb
set INSHIZEI_KENSU_COUNT=InshizeiKensuCount.opb
set NEJOUGE_DATA_CREATE=NejougeDataCreate.opb
set MIKIRI_JISEKI_DATA_CHUSYUTSU=MikiriJisekiDataChusyutsu.opb
set DPT_UCHI_URIAGE_DATA_CALC=DPTUchiUriageDataCalc.opb

#2009.07.04 VJC.���i �t�@�C���o�b�N�A�b�v�����ǉ�
# �o�b�N�A�b�v�Ώۃt�@�C���Z�b�g
set BACKUP_FILE1=SPAL.ftp
set BACKUP_FILE2=SPAL_SABUN.ftp

#----------------------------------------------------------------------
# �}�N�����s(�V�P�i���׃f�[�^�捞����)
#----------------------------------------------------------------------
echo "$SHIN_TANPIN_MEISAI_DATA_IMP START" >> $LOG_FILE
$OPA_EXE $USER_INFO \
	$EXE_MACRO_PATH/$SHIN_TANPIN_MEISAI_DATA_IMP \
	$COMMON_PATH \
	$EXE_MACRO_PATH \
	$D5T_PATH \
	$CSV_PATH \
	$CTLG_PATH \
	$COMP_CD \
 >> $LOG_FILE

# 
@ result = $status
if ( $result != 0 ) then
	echo "### $SHIN_TANPIN_MEISAI_DATA_IMP ABORT END" >> $LOG_FILE
	exit($result)
else
	# opa_execom�͐���I�����܂����B
endif
echo "$SHIN_TANPIN_MEISAI_DATA_IMP NORMAL END" >> $LOG_FILE

#----------------------------------------------------------------------
# �}�N�����s(�󎆐Ō����J�E���g����)
#----------------------------------------------------------------------
# 2009.07.03 VJC.���i ����csv�̏o�͐�w��ύX�ׁ̈A�����ǉ��i$OUT_PATH \�j
echo "$INSHIZEI_KENSU_COUNT START" >> $LOG_FILE
$OPA_EXE $USER_INFO \
	$EXE_MACRO_PATH/$INSHIZEI_KENSU_COUNT \
	$COMMON_PATH \
	$EXE_MACRO_PATH \
	$D5T_PATH \
	$CSV_PATH \
	$CTLG_PATH \
	$COMP_CD \
	$OUT_PATH \
 >> $LOG_FILE

# 
@ result = $status
if ( $result != 0 ) then
	echo "### $INSHIZEI_KENSU_COUNT ABORT END" >> $LOG_FILE
	exit($result)
else
	# opa_execom�͐���I�����܂����B
endif
echo "$INSHIZEI_KENSU_COUNT NORMAL END" >> $LOG_FILE

#----------------------------------------------------------------------
# �}�N�����s(�l�㉺�f�[�^�쐬����)
#----------------------------------------------------------------------
# 2009.07.03 VJC.���i ����csv�̏o�͐�w��ύX�ׁ̈A�����ǉ��i$OUT_PATH \�j
echo "$NEJOUGE_DATA_CREATE START" >> $LOG_FILE
$OPA_EXE $USER_INFO \
	$EXE_MACRO_PATH/$NEJOUGE_DATA_CREATE \
	$COMMON_PATH \
	$EXE_MACRO_PATH \
	$D5T_PATH \
	$CSV_PATH \
	$CTLG_PATH \
	$COMP_CD \
	$OUT_PATH \
 >> $LOG_FILE

# 
@ result = $status
if ( $result != 0 ) then
	echo "### $NEJOUGE_DATA_CREATE ABORT END" >> $LOG_FILE
	exit($result)
else
	# opa_execom�͐���I�����܂����B
endif
echo "$NEJOUGE_DATA_CREATE NORMAL END" >> $LOG_FILE

#----------------------------------------------------------------------
# �}�N�����s(���؎��уf�[�^���o����)
#----------------------------------------------------------------------
# 2009.07.03 VJC.���i ����csv�̏o�͐�w��ύX�ׁ̈A�����ǉ��i$OUT_PATH \�j
echo "$MIKIRI_JISEKI_DATA_CHUSYUTSU START" >> $LOG_FILE
$OPA_EXE $USER_INFO \
	$EXE_MACRO_PATH/$MIKIRI_JISEKI_DATA_CHUSYUTSU \
	$COMMON_PATH \
	$EXE_MACRO_PATH \
	$D5T_PATH \
	$CSV_PATH \
	$CTLG_PATH \
	$COMP_CD \
	$OUT_PATH \
 >> $LOG_FILE

# 
@ result = $status
if ( $result != 0 ) then
	echo "### $MIKIRI_JISEKI_DATA_CHUSYUTSU ABORT END" >> $LOG_FILE
	exit($result)
else
	# opa_execom�͐���I�����܂����B
endif
echo "$MIKIRI_JISEKI_DATA_CHUSYUTSU NORMAL END" >> $LOG_FILE

#----------------------------------------------------------------------
# �}�N�����s(DPT�ł�����f�[�^�W�v����)
#----------------------------------------------------------------------
# 2009.07.03 VJC.���i ����csv�̏o�͐�w��ύX�ׁ̈A�����ǉ��i$OUT_PATH \�j
echo "$DPT_UCHI_URIAGE_DATA_CALC START" >> $LOG_FILE
$OPA_EXE $USER_INFO \
	$EXE_MACRO_PATH/$DPT_UCHI_URIAGE_DATA_CALC \
	$COMMON_PATH \
	$EXE_MACRO_PATH \
	$D5T_PATH \
	$CSV_PATH \
	$CTLG_PATH \
	$COMP_CD \
	$OUT_PATH \
 >> $LOG_FILE

# 
@ result = $status
if ( $result != 0 ) then
	echo "### $DPT_UCHI_URIAGE_DATA_CALC ABORT END" >> $LOG_FILE
	exit($result)
else
	# opa_execom�͐���I�����܂����B
endif
echo "$DPT_UCHI_URIAGE_DATA_CALC NORMAL END" >> $LOG_FILE

#2009.07.04 VJC.���i �t�@�C���o�b�N�A�b�v�����ǉ�
#----------------------------------------------------------------------
# �t�@�C���o�b�N�A�b�v(�V�P�i���׃��O)
#----------------------------------------------------------------------
echo "$BACKUP_FILE1 BACKUP START" >> $LOG_FILE
mv -f $CSV_PATH/$BACKUP_FILE1 $BACKUP_PATH

# 
@ result = $status
if ( $result != 0 ) then
	echo "### $BACKUP_FILE1 BACKUP ABORT END" >> $LOG_FILE
	exit($result)
else
	# �t�@�C���o�b�N�A�b�v�͐���I�����܂����B
endif

echo "$BACKUP_FILE1 BACKUP NORMAL END" >> $LOG_FILE

#----------------------------------------------------------------------
# �t�@�C���o�b�N�A�b�v(�V�P�i���׃��O����)
#----------------------------------------------------------------------
echo "$BACKUP_FILE2 BACKUP START" >> $LOG_FILE
mv -f $CSV_PATH/$BACKUP_FILE2 $BACKUP_PATH

# 
@ result = $status
if ( $result != 0 ) then
	echo "### $BACKUP_FILE2 BACKUP ABORT END" >> $LOG_FILE
	exit($result)
else
	# �t�@�C���o�b�N�A�b�v�͐���I�����܂����B
endif

echo "$BACKUP_FILE2 BACKUP NORMAL END" >> $LOG_FILE

