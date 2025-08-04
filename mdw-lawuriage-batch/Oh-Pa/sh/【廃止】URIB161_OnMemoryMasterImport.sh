#!/bin/csh
#===============================================================================
#	�W���uID�@�@�F�@URIB161
#	�t�@�C�����@�F�@OnMemoryMasterImport.sh
#	�����@�@�@�@�F�@
#	�������e�@�@�F�@�I���������p�}�X�^�f�[�^�捞����
#	�쐬�ҁ@�@�@�F�@�V��
#	�쐬���@�@�@�F�@2009.05.26
#	�X�V�����@�@�F�@2009.07.04 VJC.���i �t�@�C���o�b�N�A�b�v�����ǉ�
#===============================================================================

# ���ʕϐ��Ǎ�
source ./CommonEnv.sh

# �W���uID�Z�b�g
set JOB_ID=URIB161
set EXE_MACRO_PATH=$MACRO_PATH/$JOB_ID

# ���O�t�@�C�����Z�b�g
set LOG_FILE=$LOG_PATH/$JOB_ID.log

# �����}�N�����Z�b�g
set ON_MEMORY_MASTER_IMPORT=OnMemoryMasterImport.opb

# �o�b�N�A�b�v�Ώۃt�@�C���Z�b�g
set BACKUP_FILE=IF_R_FI_HANBAI.csv

#----------------------------------------------------------------------
# �}�N�����s(�I���������p�}�X�^�f�[�^�捞����)
#----------------------------------------------------------------------
echo "$ON_MEMORY_MASTER_IMPORT START" >> $LOG_FILE
$OPA_EXE $USER_INFO \
	$EXE_MACRO_PATH/$ON_MEMORY_MASTER_IMPORT \
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
	echo "### $ON_MEMORY_MASTER_IMPORT ABORT END" >> $LOG_FILE
	exit($result)
else
	# opa_execom�͐���I�����܂����B
endif

echo "$ON_MEMORY_MASTER_IMPORT NORMAL END" >> $LOG_FILE

#----------------------------------------------------------------------
# �t�@�C���o�b�N�A�b�v(��v�p�̔��}�X�^)
#----------------------------------------------------------------------
echo "$BACKUP_FILE BACKUP START" >> $LOG_FILE
mv -f $CSV_PATH/$BACKUP_FILE $BACKUP_PATH

# 
@ result = $status
if ( $result != 0 ) then
	echo "### $BACKUP_FILE BACKUP ABORT END" >> $LOG_FILE
	exit($result)
else
	# �t�@�C���o�b�N�A�b�v�͐���I�����܂����B
endif

echo "$BACKUP_FILE BACKUP NORMAL END" >> $LOG_FILE

