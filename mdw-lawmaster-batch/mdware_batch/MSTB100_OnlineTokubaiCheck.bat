@echo off
REM ============================================================================
REM �^�C�g���F�I�����C�������`�F�b�N�}�X�^�쐬
REM �����@�@�F�I�����C���œ����`�F�b�N���s���ׂ̕K�v�ȃ}�X�^�쐬���s���B
REM version 3.00 2013/05/23 M.Ayukawa:�V�K
REM ============================================================================

call %~dp0\SetBatchPath.bat

REM == MB83-01-51 TMP BAT�}�X�^�쐬����
%execjava% mdware.common.batch.util.control.BatchController MB83-01-51 %CLS_HOME%\batch\properties release use
IF %ERRORLEVEL% NEQ 0 goto error

REM == MB83-02-51 �X�ʏ��i�W�J����
%execjava% mdware.common.batch.util.control.BatchController MB83-02-51 %CLS_HOME%\batch\properties release use
IF %ERRORLEVEL% NEQ 0 goto error

REM == MB83-04-51 �X�ʗ�O���f����
%execjava% mdware.common.batch.util.control.BatchController MB83-04-51 %CLS_HOME%\batch\properties release use
IF %ERRORLEVEL% NEQ 0 goto error

REM == MBA2-18-51 IF���i�}�X�^�쐬
%execjava% mdware.common.batch.util.control.BatchController MBA2-18-51 %CLS_HOME%\batch\properties release use
IF %ERRORLEVEL% NEQ 0 goto error

REM == MBA2-22-51 IF�X�ʏ��i�f�[�^�쐬����
%execjava% mdware.common.batch.util.control.BatchController MBA2-22-51 %CLS_HOME%\batch\properties release use
IF %ERRORLEVEL% NEQ 0 goto error

:end

exit 0

:error

exit 1
