@echo off
rem =======================================
rem MSTB201-030 �C�j�V����PLU�X�ʏ��i�}�X�^�쐬
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB201-030 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%
